package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * 【收纳工具】物品：把「自己拥有的」实体完整收进物品，再从物品还原实体。
 *
 * <p>三个注册项共用本类，靠 {@link Scope} 区分：
 * <ul>
 *   <li>{@code storage_tool} —— 空符，{@code scope = null}，无收纳能力，只作为「还没装东西」的形态；</li>
 *   <li>{@code storage_tool_manor} —— 纸鸢满符，{@link Scope#MANOR}，只收本模组的实体；</li>
 *   <li>{@code storage_tool_vanilla} —— 原版满符，{@link Scope#VANILLA}，只收原版可拥有的宠物。</li>
 * </ul>
 *
 * <p>采用「多物品共类 + 换物品」而非「单一物品 + NBT 判断」，是为了让满/空外观差别直接用贴图表达，
 * 不需要任何动态模型渲染机制（做法参考 TouhouLittleMaid 的 {@code ItemSmartSlab}）。
 *
 * <p><b>职责边界</b>：本类只负责「数据层」—— 能不能收、怎么序列化、体积是否超标。
 * 交互入口（右击实体收纳 / 右击地面释放）在 {@code event/RightClickEvent} 与 {@link #useOn} 中接线，
 * 分别由任务点 T4 / T3 落地。
 */
public class StorageToolItem extends Item {
    private static final Logger LOGGER = LoggerFactory.getLogger("PaperKiteManor/StorageTool");

    /**
     * 原版可收纳白名单：只放**可驯服且真正有主人**的宠物。
     *
     * <p><b>为什么用 {@link EntityType} 常量精确比对，而不是 {@code instanceof}</b>：
     * ① 原版猫/马的子类与变种较多，逐个类判断容易漏；② 用 {@code EntityType} 比对在将来的
     * 原版版本里是唯一稳定的写法；③ 白名单是「显式同意」语义 —— 宁可玩家发现某只收不了，
     * 也不要误收不该收的东西。
     *
     * <p><b>刻意排除</b>：
     * <ul>
     *   <li><b>狐狸</b> —— 它用的是「信任」（{@code TrustingAnimal}）机制而非主人制，
     *       {@code getOwnerUUID()} 恒为 null，塞进通用判定需要写特例，违背白名单的简洁性；</li>
     *   <li><b>豹猫 / 未驯服的猫</b> —— {@link #isOwnedBy} 已经会拦掉「没有主人」的个体，
     *       所以白名单里放宽到 {@code CAT} 类型本身，不必再区分驯服与否。</li>
     * </ul>
     */
    private static final Set<EntityType<?>> VANILLA_WHITELIST = createVanillaWhitelist();

    /** 该工具的收纳范围。{@code null} 表示空符（不具备收纳能力）。 */
    @Nullable
    private final Scope scope;

    public StorageToolItem(Properties properties, @Nullable Scope scope) {
        super(properties);
        this.scope = scope;
    }

    @Nullable
    public Scope getScope() {
        return this.scope;
    }

    /** 本工具是否具备收纳能力（空符返回 {@code false}）。 */
    public boolean canStoreAnything() {
        return this.scope != null;
    }

    /**
     * 该实体此刻能否被本工具收纳。
     *
     * <p>四项前置校验，任一不过即拒收：实体存活 → 不是玩家 → 落在本工具范围内 → 主人是本人。
     *
     * <p>注意 {@code isRemoved()} 也在检查之列：防连点导致同一实体被收纳两次
     * （第一次已 {@code discard()} 的实体不应再被写入第二件物品）。
     */
    public boolean canStore(Entity entity, Player player) {
        if (this.scope == null) {
            return false;
        }
        if (!entity.isAlive() || entity.isRemoved()) {
            return false;
        }
        if (entity instanceof Player) {
            // 玩家不在任何范围内；显式拦掉以免将来范围放宽时误伤
            return false;
        }
        if (!this.scope.matches(entity)) {
            return false;
        }
        return isOwnedBy(entity, player);
    }

    /** 实体主人是否为该玩家。无主人的实体（未驯服个体、狐狸等）一律返回 {@code false}。 */
    public static boolean isOwnedBy(Entity entity, Player player) {
        if (!(entity instanceof OwnableEntity ownable)) {
            return false;
        }
        var ownerUuid = ownable.getOwnerUUID();
        return ownerUuid != null && ownerUuid.equals(player.getUUID());
    }

    /**
     * 把实体的完整数据写进物品，并返回结果。
     *
     * <p>调用方（T4 的事件层）在收到 {@link Result#SUCCESS} 后才应移除实体 ——
     * 本方法<b>不做世界改动</b>，失败时物品保持原样，实体必须留在世界里。
     *
     * <p>返回 {@link Result#TOO_LARGE} 时会往服务端日志写一行 WARN（含实测体积），
     * 便于事后排查是哪种实体把体积顶上去的。
     */
    public Result writeEntityData(ItemStack stack, Entity entity) {
        CompoundTag data = new CompoundTag();
        entity.saveWithoutId(data);

        CompoundTag itemTag = stack.getOrCreateTag();
        itemTag.putString(StorageToolNbt.ENTITY_TYPE, entityTypeId(entity));
        itemTag.putLong(StorageToolNbt.STORED_AT, entity.level().getGameTime());
        itemTag.put(StorageToolNbt.ROOT, data);

        int size = estimateSize(itemTag);
        if (size > StorageToolNbt.MAX_DATA_SIZE) {
            // 回滚，避免半成品数据留在物品上（体积守卫是「拒绝」而不是「截断」）
            stack.setTag(null);
            LOGGER.warn("拒绝收纳：物品 NBT 约 {} 字节，超过上限 {} 字节（实体类型 {}）",
                    size, StorageToolNbt.MAX_DATA_SIZE, entityTypeId(entity));
            return Result.TOO_LARGE;
        }
        return Result.SUCCESS;
    }

    /**
     * 丢弃「正被玩家打开的容器」，堵住「收纳后仍能从旧容器隔空取物」的漏洞。
     *
     * <p>背景：本模组女仆的背包界面 {@code BunnySuitcaseContainer} 持有实体引用，
     * 其 {@code stillValid()} 只看 {@code isAlive()}，而 {@code discard()} 并不让该值为假 ——
     * 所以玩家可以「先打开女仆背包 → 再把她收进符 → 继续从已消失的实体身上掏东西」。
     * 这里在收纳前主动关掉界面，让容器在服务端与客户端一起失效。
     */
    public static void closeOpenContainer(Entity entity, Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }
        // 实体自己实现了 MenuProvider 时（女仆 / 黑猫），直接关掉它开的那个界面
        if (entity instanceof net.minecraft.world.MenuProvider provider) {
            AbstractContainerMenu menu = serverPlayer.containerMenu;
            if (menu != null && !menu.stillValid(serverPlayer)) {
                serverPlayer.closeContainer();
            }
            return;
        }
        // 兜底：无论目标是不是 MenuProvider，只要玩家手上有不属于自己的容器就关掉
        if (serverPlayer.containerMenu != null && !serverPlayer.containerMenu.stillValid(serverPlayer)) {
            serverPlayer.closeContainer();
        }
    }

    /** 实体类型 id，形如 {@code papercraft_magic_decoration:white_rabbit_maid}。 */
    public static String entityTypeId(Entity entity) {
        ResourceLocation key = EntityType.getKey(entity.getType());
        return key == null ? "minecraft:unknown" : key.toString();
    }

    private static Set<EntityType<?>> createVanillaWhitelist() {
        Set<EntityType<?>> set = Collections.newSetFromMap(new IdentityHashMap<>());
        set.add(EntityType.WOLF);
        set.add(EntityType.CAT);
        set.add(EntityType.PARROT);
        set.add(EntityType.HORSE);
        set.add(EntityType.DONKEY);
        set.add(EntityType.MULE);
        set.add(EntityType.LLAMA);
        // 商人羊驼同样是可驯服、有主人的原版宠物，且与羊驼同属 Llama 类，一并放行
        set.add(EntityType.TRADER_LLAMA);
        return Collections.unmodifiableSet(set);
    }

    /**
     * 估算 NBT 序列化后的字节数。
     *
     * <p>用「未压缩的裸 NBT」而非 {@code CompoundTag#sizeInBytes()}（后者会算上堆对象开销，
     * 数值偏大且随 JVM 实现浮动）；也刻意不用压缩，因为体积守卫的目标是拦住
     * 「会让物品栏同步变得很贵」的数据，而未压缩体积是那个成本的稳定上界。
     */
    private static int estimateSize(CompoundTag tag) {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream(1024);
             DataOutputStream out = new DataOutputStream(bytes)) {
            NbtIo.write(tag, out);
            out.flush();
            return bytes.size();
        } catch (IOException e) {
            LOGGER.warn("估算 NBT 体积失败，按上限处理以拒绝收纳", e);
            return Integer.MAX_VALUE;
        }
    }

    /** {@link #writeEntityData} 的结果。 */
    public enum Result {
        /** 已写入物品。 */
        SUCCESS,
        /** 数据体积超过 {@link StorageToolNbt#MAX_DATA_SIZE}，物品未被改动。 */
        TOO_LARGE
    }

    /**
     * 收纳范围。两个满符各自独立判定，互不越界 —— 用原版版去收女仆、或用纸鸢版去收狼，
     * 都应被拒绝。
     */
    public enum Scope {
        /** 本模组实体：命名空间等于 {@code papercraft_magic_decoration}。 */
        MANOR {
            @Override
            public boolean matches(Entity entity) {
                ResourceLocation key = EntityType.getKey(entity.getType());
                return key != null && PaperKiteManor.MOD_ID.equals(key.getNamespace());
            }
        },
        /** 原版宠物：命中 {@link #VANILLA_WHITELIST}。 */
        VANILLA {
            @Override
            public boolean matches(Entity entity) {
                return VANILLA_WHITELIST.contains(entity.getType());
            }
        };

        /** 该实体是否落在本范围内（只判类型，不判主人）。 */
        public abstract boolean matches(Entity entity);
    }
}
