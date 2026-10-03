package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.EntityBoundMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
     * 释放后的物品冷却（tick）。防连点导致同一件工具被连续释放两次
     * （参考实现 TouhouLittleMaid 同样使用 20 tick，见 {@code ItemSmartSlab.java:145}）。
     */
    public static final int RELEASE_COOLDOWN_TICKS = 20;

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

    /** 白名单的人类可读描述，只用于日志与诊断文案（避免与 {@link #VANILLA_WHITELIST} 漂移）。 */
    private static final String VANILLA_WHITELIST_DESC = "狼 / 猫 / 鹦鹉 / 马 / 驴 / 骡 / 羊驼 / 商人羊驼";

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
     * <p><b>关键语义（2026-10-03 修正）</b>：{@link Scope} 描述的是「工具**里装着**的东西属于哪个范围」，
     * 不是「准入资格」。所以：
     * <ul>
     *   <li><b>空符</b>（{@code scope == null}）是**唯一能发起收纳**的形态 —— 它收纳任何范围内的实体，
     *       成功后由 {@link #swapToFullTool} 换成对应范围的满符；</li>
     *   <li><b>满符</b>（{@code scope != null}）已经装着东西，只能释放；若要收纳新实体，
     *       必须先在物品栏里切回空符。</li>
     * </ul>
     *
     * <p>⚠️ 曾经的实现把这里写成「空符一律拒收」，导致<b>满符永远无法被产生</b> ——
     * 收纳能力被装在了唯一不能收纳的物品上，整条链在第一步就断了。
     */
    public boolean canStore(Entity entity, Player player) {
        return refuseReason(this.scope, entity, player) == null;
    }

    /**
     * 判定该实体能否被「scope 的工具」收纳；可以则返回 {@code null}，否则返回**拒绝原因**。
     *
     * <p>把判定收敛到这一处的理由：{@link #canStore} 只回答「行不行」，
     * 而排查游戏内问题时需要知道「为什么不行」。两处各写一份判定迟早会漂移，
     * 所以只保留这一份实现，{@link #canStore} 与诊断日志都调它。
     */
    @Nullable
    public static String refuseReason(@Nullable Scope scope, Entity entity, Player player) {
        // 满符已经装着东西，不能再收 —— 这是「形态」限制，与实体本身无关
        if (scope != null) {
            return "手持的是满符（scope=" + scope + "），请先在物品栏里切回空符再收纳";
        }
        if (entity == player) {
            return "目标是玩家自己";
        }
        if (!entity.isAlive()) {
            return "实体 isAlive()=false";
        }
        if (entity.isRemoved()) {
            return "实体 isRemoved()=true（已从世界移除）";
        }
        if (entity instanceof Player) {
            return "目标是玩家";
        }
        // 空符收纳任何范围内的实体，具体归属由 Scope.forEntity 决定
        Scope targetScope = Scope.forEntity(entity);
        if (targetScope == null) {
            return "不属于任何可收纳范围：实体类型 id = " + entityTypeId(entity)
                    + "（纸鸢版收本模组实体，原版版收 " + VANILLA_WHITELIST_DESC + "）";
        }
        if (!(entity instanceof OwnableEntity ownable)) {
            return "实体未实现 OwnableEntity，无法取得主人";
        }
        var ownerUuid = ownable.getOwnerUUID();
        if (ownerUuid == null) {
            return "实体没有主人（getOwnerUUID()=null，可能未真正驯服或驯服数据未落盘）";
        }
        if (!ownerUuid.equals(player.getUUID())) {
            return "主人不匹配：实体主人=" + ownerUuid + "，当前玩家=" + player.getUUID();
        }
        return null;
    }

    /** 决定该实体收纳后应变成哪个范围的满符。 */
    @Nullable
    public static Scope scopeFor(Entity entity) {
        return Scope.forEntity(entity);
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
        // 实体自身数据
        CompoundTag data = new CompoundTag();
        entity.saveWithoutId(data);
        // ⚠️ 类型 id 必须写进 EntityData **内部**，与 StorageToolNbt 的契约一致；
        // 写到物品根上会导致 readEntityType 永远读不到（F3 的根因，见任务点清单「一之五」）。
        data.putString(StorageToolNbt.ENTITY_TYPE, entityTypeId(entity));

        CompoundTag itemTag = stack.getOrCreateTag();
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
     * 释放入口：手持满符右击方块时，把它记录的实体安置到点击面的外侧。
     *
     * <p>放在 {@code onItemUseFirst} 而不是 {@code Item#useOn}：满符是强语义的功能物品，
     * 应当优先于方块自身的交互（否则「右击箱子」会先开箱子、符不生效）。
     * 返回 {@link InteractionResult#SUCCESS} 会阻止方块交互继续。
     *
     * <p>失败路径一律给**可读的中文提示**（lang key 见任务点清单 T5），
     * 而不是静默什么都不做 —— 玩家必须知道「为什么放不出来」。
     */
    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        // 诊断日志：第一行无条件记录，用来判断「这个钩子到底有没有被调用」。
        // 定位完毕后降级为 debug 或删除。
        LOGGER.info("[收纳工具/诊断-释放] 进入 onItemUseFirst side={} hand={} 玩家={} 有数据={} 点击方块={} 面={}",
                level.isClientSide() ? "CLIENT" : "SERVER",
                context.getHand(),
                player == null ? "null" : player.getName().getString(),
                StorageToolNbt.hasEntityData(stack),
                context.getClickedPos(),
                context.getClickedFace());

        // 把物品根 NBT 的键与 EntityData 的键直接打出来 —— 这是判断「数据到底写成了什么形状」
        // 最直接的手段，比逐层断言快得多。
        // ⚠️ 类型 id 应出现在 EntityData **内部**（契约见 StorageToolNbt）；出现在根上即为写错位置。
        if (stack.getTag() != null) {
            CompoundTag entityData = StorageToolNbt.getEntityData(stack);
            LOGGER.info("[收纳工具/诊断-释放] 物品根键={} EntityData 键数={} EntityData 内的类型 id={}",
                    stack.getTag().getAllKeys(),
                    entityData == null ? "null" : entityData.getAllKeys().size(),
                    entityData == null ? "null" : "'" + entityData.getString(StorageToolNbt.ENTITY_TYPE) + "'");
        }

        if (player == null) {
            return InteractionResult.PASS;
        }
        // 空符（无数据）不归本方法管，交回默认流程
        if (!StorageToolNbt.hasEntityData(stack)) {
            return InteractionResult.PASS;
        }
        // 只处理主手，避免一次右击触发两次
        if (context.getHand() != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide()) {
            // 世界改动只在服务端做；返回 SUCCESS 让客户端走「成功」的挥手表现
            return InteractionResult.SUCCESS;
        }

        // ① 释放鉴权：只有物品里记录的实体主人本人能放出来，否则符被捡走就等于宠物易主
        if (!canReleaseBy(stack, player)) {
            LOGGER.info("[收纳工具/诊断-释放] 鉴权失败：物品记录的主人={} 当前玩家={}",
                    readOwnerUuid(stack), player.getUUID());
            player.displayClientMessage(
                    Component.translatable("item.papercraft_magic_decoration.storage_tool.not_your_pet"), true);
            return InteractionResult.FAIL;
        }

        // ② 实体类型必须能查到 —— 物品来自已卸载的模组时这里为 null，绝不能直接往下走
        EntityType<?> type = readEntityType(stack);
        String recordedId = "";
        CompoundTag stored = StorageToolNbt.getEntityData(stack);
        if (stored != null) {
            recordedId = stored.getString(StorageToolNbt.ENTITY_TYPE);
        }
        LOGGER.info("[收纳工具/诊断-释放] 鉴权通过，记录的实体 id='{}' 反查类型={}", recordedId, type);
        if (type == null) {
            player.displayClientMessage(
                    Component.translatable("item.papercraft_magic_decoration.storage_tool.unknown_entity"), true);
            return InteractionResult.FAIL;
        }

        // ③ 创建实例（create 可能返回 null，例如该类型在当前环境不可用）
        Entity spawned = type.create(level);
        LOGGER.info("[收纳工具/诊断-释放] type.create() 结果={}", spawned);
        if (spawned == null) {
            player.displayClientMessage(
                    Component.translatable("item.papercraft_magic_decoration.storage_tool.unknown_entity"), true);
            return InteractionResult.FAIL;
        }

        // ④ 先读档，再定位（顺序不可颠倒，否则存档里的旧 Pos 会覆盖新位置）
        CompoundTag data = StorageToolNbt.getEntityData(stack);
        if (data != null) {
            spawned.load(data);
        }
        BlockPos spawnPos = context.getClickedPos().relative(context.getClickedFace());
        LOGGER.info("[收纳工具/诊断-释放] 读档完成，准备在 {} 入世（level={}）", spawnPos, level.getClass().getSimpleName());
        if (level instanceof ServerLevel serverLevel) {
            placeEntity(spawned, serverLevel, spawnPos);
            LOGGER.info("[收纳工具/诊断-释放] addFreshEntity 已调用，实体 UUID={} 位置={}",
                    spawned.getUUID(), spawned.blockPosition());
        } else {
            LOGGER.warn("[收纳工具/诊断-释放] level 不是 ServerLevel，实体未入世：{}", level.getClass().getName());
        }

        // ⑤ 换回空符 + 冷却，防连点重复释放
        player.setItemInHand(context.getHand(), createEmptyTool());
        player.getInventory().setChanged();
        player.getCooldowns().addCooldown(this, RELEASE_COOLDOWN_TICKS);
        LOGGER.info("[收纳工具/诊断-释放] 已换回空符，流程结束");
        return InteractionResult.SUCCESS;
    }

    /**
     * 清空物品上的实体数据，让它回到「空」状态。
     *
     * <p>正常释放流程走的是「换成空符物品」（见 {@code onItemUseFirst}），
     * 本方法用于**异常路径**与**测试**（例如数据写坏时把物品恢复成可用状态），
     * 不改变物品 id。
     */
    public static void clearEntityData(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            return;
        }
        // 移除 ROOT 即等于移除整个实体数据（类型 id 与时间戳都在其内部/同级；
        // 注意 ENTITY_TYPE 位于 EntityData **内部**，随 ROOT 一起被移除）。
        tag.remove(StorageToolNbt.ROOT);
        tag.remove(StorageToolNbt.STORED_AT);
        if (tag.isEmpty()) {
            stack.setTag(null);
        }
    }

    /**
     * 关掉「正被该实体占用的容器」，堵住「收纳后仍能从已消失的实体隔空取物」的漏洞。
     *
     * <p><b>漏洞成因</b>：本模组女仆的背包界面 {@code BunnySuitcaseContainer} 持有实体引用，
     * 其 {@code stillValid()} 原本只看 {@code isAlive()}，而 {@code discard()} 并不让该值为假 ——
     * 所以玩家可以「先打开女仆背包 → 再把她收进符 → 继续从已消失的实体身上掏东西」。
     *
     * <p><b>为什么不能判 {@code stillValid()}</b>：界面在打开的那一刻当然是有效的，
     * 用「是否失效」当条件等于永远不关。必须判断<b>玩家当前开着的界面是否来自这个实体</b>。
     *
     * <p><b>两道防线</b>：
     * <ol>
     *   <li>本方法（主动关闭）—— 靠 {@link EntityBoundMenu#getMenuEntity()} 精确比对实体身份；</li>
     *   <li>{@code BunnySuitcaseContainer} / {@code LobbyBoyBackpackContainer} 的 {@code stillValid()}
     *       已补上 {@code !isRemoved()} —— 即使第 1 道漏了，界面也会在下一 tick 自动失效。</li>
     * </ol>
     */
    public static void closeOpenContainer(Entity entity, Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return;
        }
        AbstractContainerMenu menu = serverPlayer.containerMenu;
        if (menu == null) {
            return;
        }
        // 只有「这个实体开的界面」才关；玩家在看自己的背包 / 箱子时不该被牵连
        if (menu instanceof EntityBoundMenu bound && bound.getMenuEntity() == entity) {
            serverPlayer.closeContainer();
        }
    }

    /**
     * 从物品里读出实体类型。查不到时返回 {@code null}（调用方必须处理，否则会崩服）。
     *
     * <p>查不到的真实场景：这件物品的 NBT 来自某个**已卸载的模组**，或存档被外部工具改坏。
     */
    @Nullable
    public static EntityType<?> readEntityType(ItemStack stack) {
        CompoundTag data = StorageToolNbt.getEntityData(stack);
        if (data == null) {
            return null;
        }
        String id = data.getString(StorageToolNbt.ENTITY_TYPE);
        if (id.isEmpty()) {
            return null;
        }
        return EntityType.byString(id).orElse(null);
    }

    /**
     * 这件物品里记录的实体主人（用于释放侧鉴权）。
     *
     * <p>取的是实体 NBT 自带的 {@code Owner} 键（原版狼/猫与 {@code TamableAnimal} 都写它），
     * 不是本模组另加的字段。
     *
     * @return 主人 UUID；无主人（未驯服个体）或键缺失时返回 {@code null}
     */
    @Nullable
    public static java.util.UUID readOwnerUuid(ItemStack stack) {
        CompoundTag data = StorageToolNbt.getEntityData(stack);
        if (data == null || !data.hasUUID(StorageToolNbt.OWNER)) {
            return null;
        }
        return data.getUUID(StorageToolNbt.OWNER);
    }

    /**
     * 释放鉴权：只有**物品里记录的实体主人本人**能把它放出来。
     *
     * <p>为什么释放也要校验：否则玩家 A 把宠物收进符后，符一旦被玩家 B 捡到 / 拿走，
     * B 就能把 A 的宠物据为己有。参考实现 TouhouLittleMaid 同样在释放侧做这道校验
     * （{@code ItemSmartSlab.java:125-132}）。
     *
     * @return 数据里没有主人记录时返回 {@code true}（原版生物蛋式「无主物品」应可被任何人释放），
     *         有记录时要求与玩家一致
     */
    public static boolean canReleaseBy(ItemStack stack, Player player) {
        var owner = readOwnerUuid(stack);
        return owner == null || owner.equals(player.getUUID());
    }

    /**
     * 把一个实体安置到目标位置并放入世界。
     *
     * <p>骨架借用原版生物蛋的放置流程（{@code SpawnEggItem#useOn}）：
     * <b>创建 → 读档 → 定位 → 入世</b>。两处刻意偏离生物蛋：
     * <ol>
     *   <li><b>不调 {@code finalizeSpawn}</b> —— 它会按难度随机化属性，会把女仆的血量、自定义名冲掉；</li>
     *   <li>不使用 {@code MobSpawnType.SPAWN_EGG}，避免触发刷怪相关逻辑。</li>
     * </ol>
     *
     * <p><b>顺序要害</b>：必须先 {@code load} 再 {@code moveTo}。反过来会被 {@code load} 读回的
     * 旧坐标覆盖 —— 因为 {@code saveWithoutId} 把 {@code Pos}/{@code Motion}/{@code FallDistance}
     * 也一并写了出来（已核实）。
     */
    public static void placeEntity(Entity entity, ServerLevel level, BlockPos pos) {
        entity.moveTo(pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                entity.getYRot(), entity.getXRot());
        entity.setDeltaMovement(Vec3.ZERO);
        entity.resetFallDistance();

        // 保险：若目标世界已存在同 UUID 的实体（创造模式中键复制、旧世界残留），重新生成 UUID。
        // 注意这一步必须在 addFreshEntity 之前做，且要基于实体当前的 UUID 判断。
        if (level.getEntity(entity.getUUID()) != null) {
            LOGGER.warn("释放时发现 UUID 冲突（{}），已为 {} 重新生成 UUID",
                    entity.getUUID(), entityTypeId(entity));
            entity.setUUID(UUID.randomUUID());
        }

        level.addFreshEntity(entity);
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

    // ------------------------------------------------------------------
    // 满符 / 空符互转与「满符」的展示、保护行为（T4）
    // ------------------------------------------------------------------

    /**
     * 按**归属范围**构造对应的满符。
     *
     * <p>⚠️ 参数是「实体应该归入的范围」，**不是**工具自己的 {@code scope} ——
     * 收纳是由空符（{@code scope == null}）发起的，用工具自己的 scope 会永远拿到 {@code null}，
     * 于是换符失败、实体不被移除，功能完全失效（这是 2026-10-03 修正的第二个缺陷）。
     *
     * <p>取值方式是延迟解析 {@code ModItems} 的 {@code RegistryObject}，
     * 而不是构造期捕获 {@code Item} 实例 —— 物品注册期 {@code RegistryObject} 尚未解析完成，
     * 提前取会在启动时抛异常。
     */
    @Nullable
    private static ItemStack createFullTool(@Nullable Scope targetScope) {
        if (targetScope == null) {
            return null;
        }
        return switch (targetScope) {
            case MANOR -> ModItems.STORAGE_TOOL_MANOR.get().getDefaultInstance();
            case VANILLA -> ModItems.STORAGE_TOOL_VANILLA.get().getDefaultInstance();
        };
    }

    /** 满符释放后应该换回的空符。 */
    private static ItemStack createEmptyTool() {
        return ModItems.STORAGE_TOOL.get().getDefaultInstance();
    }

    /**
     * 结算「收纳一次」：把手上的空符换成**该实体归属范围**的满符，并把实体数据带过去。
     *
     * <p><b>⚠️ 数据必须显式转移（2026-10-03 修正的第三个缺陷）</b>：
     * 实体 NBT 是写在 {@code stack} 上的，而 {@code createFullTool} 出来的是一个
     * **全新的空满符**。先前直接 {@code setItemInHand(hand, full)} 会把写着数据的 stack 整个丢弃，
     * 结果就是「实体被收走了，但符里什么都没有」—— 表现为收纳看似成功、却永远放不出来。
     *
     * <p>用 {@code setTag(originalTag.copy())} 而不是直接传引用：新 stack 应当拥有自己的 NBT，
     * 避免两个 ItemStack 共享同一个 CompoundTag 而在后续被无意中互相改动。
     *
     * <p>用 {@code player.setItemInHand} 而不是往光标上放物品 —— 收纳时玩家的光标上
     * 可能正拿着别的东西，用光标会把它顶掉。
     *
     * @param entity 被收纳的实体，用它的归属范围决定换成哪个满符
     * @return 成功时返回 {@code true}；实体不属于任何范围、或物品未注册时返回 {@code false}
     *         （调用方**必须**据此放弃收纳，否则数据会随被换掉的 stack 一起消失）
     */
    public boolean swapToFullTool(ItemStack stack, Player player, InteractionHand hand, Entity entity) {
        ItemStack full = createFullTool(Scope.forEntity(entity));
        if (full == null) {
            return false;
        }
        // 把刚写入的实体数据搬到新物品上 —— 漏掉这一步就等于把宠物数据丢进虚空
        CompoundTag data = stack.getTag();
        full.setTag(data == null ? null : data.copy());
        player.setItemInHand(hand, full);
        // 满符与原空符不同 id，必须显式标记库存变化，否则客户端仍显示空符外观
        player.getInventory().setChanged();
        LOGGER.info("[收纳工具/诊断] 换符完成：新物品={} 新物品有数据={}",
                full.getItem(), StorageToolNbt.hasEntityData(full));
        return true;
    }

    /**
     * 满符自带附魔光效（TLM 做法）—— 让「装了东西」在远处也能一眼看出来，
     * 不必悬停读文字。
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        return this.scope != null || super.isFoil(stack);
    }

    /**
     * 满符**不允许**放进容器（潜影盒 / 箱子等）。
     *
     * <p>理由：满符里装着实体数据，允许装箱等于开了「批量搬运实体数据」的口子，
     * 是复制/刷实体面的温床。参考实现 TouhouLittleMaid 同样如此
     * （{@code ItemSmartSlab.java:184-187}）。
     */
    @Override
    public boolean canFitInsideContainerItems() {
        return this.scope == null && super.canFitInsideContainerItems();
    }

    /**
     * 掉在地上的满符：设为发光 + 无敌，并在掉到世界底部前拦住。
     *
     * <p>目的很直接 —— 里面装着玩家的宠物，被岩浆烧掉或被虚空吞掉都是**不可逆**的损失。
     * 这里的三重保护让「失手丢出去」不会变成灾难（TLM 做法，{@code AbstractStoreMaidItem.java:38-53}）。
     */
    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (this.scope != null) {
            if (!entity.isCurrentlyGlowing()) {
                entity.setGlowingTag(true);
            }
            if (!entity.isInvulnerable()) {
                entity.setInvulnerable(true);
            }
            double minY = entity.level().getMinBuildHeight();
            Vec3 pos = entity.position();
            if (pos.y < minY) {
                entity.setNoGravity(true);
                entity.setDeltaMovement(Vec3.ZERO);
                entity.setPos(pos.x, minY, pos.z);
            }
        }
        return super.onEntityItemUpdate(stack, entity);
    }

    /**
     * 满符的悬浮说明：实体名 / 类型 / 生命值 / 背包件数。
     *
     * <p>这既是给玩家的信息（「我这符里装的是谁」），也是**验收手段** ——
     * 只看这一行就能判断数据有没有正确写进去，不需要会任何指令。
     *
     * <p>注意：这里只做**只读**解析，不在客户端创建实体实例。
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (!StorageToolNbt.hasEntityData(stack)) {
            return;
        }
        CompoundTag data = StorageToolNbt.getEntityData(stack);
        if (data == null) {
            return;
        }
        String typeId = data.getString(StorageToolNbt.ENTITY_TYPE);
        EntityType<?> type = typeId.isEmpty() ? null : EntityType.byString(typeId).orElse(null);

        // 实体名：优先自定义名，否则用实体类型的默认显示名
        String name = data.contains("CustomName", Tag.TAG_STRING)
                ? data.getString("CustomName")
                : (type != null ? Component.translatable(type.getDescriptionId()).getString() : typeId);

        tooltip.add(Component.literal(name).withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.translatable("item.papercraft_magic_decoration.storage_tool.lore.type",
                typeId.isEmpty() ? "?" : typeId).withStyle(ChatFormatting.DARK_GRAY));

        if (data.contains("Health")) {
            float health = data.getFloat("Health");
            tooltip.add(Component.translatable("item.papercraft_magic_decoration.storage_tool.lore.health",
                    String.format("%.1f", health)).withStyle(ChatFormatting.GRAY));
        }

        // 背包件数：女仆/黑猫的背包是 ItemStackHandler 写在 inventory 键里；黑猫另有 Items 键
        int items = countStoredItems(data);
        if (items > 0) {
            tooltip.add(Component.translatable("item.papercraft_magic_decoration.storage_tool.lore.items",
                    items).withStyle(ChatFormatting.GRAY));
        }
        tooltip.add(Component.translatable("item.papercraft_magic_decoration.storage_tool.lore.release")
                .withStyle(ChatFormatting.DARK_GREEN));
    }

    /**
     * 统计实体 NBT 里装了多少件物品。
     *
     * <p>只认识本模组与 Forge 的两种写法（女仆族的 {@code inventory} = {@code ItemStackHandler}，
     * 原版马/羊驼的 {@code Items} = 列表）；认不出来就报 0，不猜、不遍历全部 NBT。
     */
    private static int countStoredItems(CompoundTag data) {
        int count = 0;
        if (data.contains("inventory", Tag.TAG_COMPOUND)) {
            CompoundTag inv = data.getCompound("inventory");
            for (String key : inv.getAllKeys()) {
                // Forge 的 ItemStackHandler 把每个槽位写成 "0".."n" 的子 compound；
                // 空槽也存在但内容为空，靠 isEmpty() 过滤。
                // 这里刻意用 getCompound 而不是 getTag：后者在当前映射下不可用。
                CompoundTag slot = inv.getCompound(key);
                if (!slot.isEmpty()) {
                    count += slot.getInt("Count");
                }
            }
        }
        if (data.contains("Items", Tag.TAG_LIST)) {
            ListTag list = data.getList("Items", Tag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                count += list.getCompound(i).getInt("Count");
            }
        }
        return count;
    }

    /**
     * 收纳后的**归属范围** —— 描述「满符里装着的东西属于哪一类」，不是「准入资格」。
     *
     * <p>语义修正（2026-10-03）：早先把它当成准入判定用，导致空符（{@code scope == null}）
     * 一律拒收、于是满符永远无法被产生。现在的正确分工是：
     * 空符收纳**任意**范围内的实体，由 {@link #forEntity} 决定该变成哪个满符。
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

        /**
         * 该实体收纳后应归入哪个范围；两类都不匹配时返回 {@code null}。
         *
         * <p>顺序有意义：先判本模组，再判原版白名单。本模组实体一定是
         * {@code papercraft_magic_decoration} 命名空间，不可能落进原版白名单，所以顺序只影响可读性，
         * 不影响结果。
         */
        @Nullable
        public static Scope forEntity(Entity entity) {
            for (Scope scope : values()) {
                if (scope.matches(entity)) {
                    return scope;
                }
            }
            return null;
        }
    }
}
