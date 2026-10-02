package com.kazi_cat.papercraft_magic_decoration.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

/**
 * 「收纳工具」的 NBT 数据契约。
 *
 * <p>所有与收纳实体相关的 NBT 键名与阈值集中在 {@code item/StorageToolNbt} 一处定义，
 * 收纳（写）、释放（读）、校验（尺寸守卫）三处必须全部引用本类常量，禁止再硬编码字面量。
 *
 * <p><b>数据结构</b>（挂在物品根的 {@link CompoundTag} 上）：
 * <pre>
 * {                         // 物品根 Tag
 *   "EntityData": {         // {@link #ROOT} —— saveWithoutId 的完整实体数据
 *     "Owner":    [I; ...], // {@link #OWNER}    —— 实体主人 UUID（用于校验归属）
 *     "UUID":     [I; ...], // 实体自身 UUID（saveWithoutId 自带，用于同一性判定）
 *     "Pos":      [D; ...], // saveWithoutId 自带，释放时必须覆盖
 *     "Motion":   [D; ...], // saveWithoutId 自带，释放时必须覆盖
 *     ...                   // 实体自身的其余存档数据
 *   },
 *   "EntityType": "papercraft_magic_decoration:white_rabbit_maid", // {@link #ENTITY_TYPE}
 *   "StoredAt":   12345L    // {@link #STORED_AT} —— 收纳时的 gameTime
 * }
 * </pre>
 *
 * <p><b>为何不存字段名而不存 {@code EntityType} 实例</b>：NBT 必须可跨版本、跨整合包序列化，
 * 只能落字符串 id；释放时用 {@code EntityType.byString} 反查，并必须处理「查不到」的情况
 * （存档来自已卸载的模组）。
 *
 * <p>类名特意用 {@code StorageToolNbt} 而非 {@code StorageToolData}：不叫 Data 是为了防止后来者
 * 把非 NBT 的状态（冷却、配置、缓存）也塞进来。
 */
public final class StorageToolNbt {
    /**
     * 物品根 Tag 下、承载完整实体数据的子 Tag 键。
     *
     * <p>存在此键即视为「已装载实体」；释放后删除此键，物品回归空状态。
     */
    public static final String ROOT = "EntityData";

    /** {@link #ROOT} 内：实体类型 id（形如 {@code papercraft_magic_decoration:white_rabbit_maid}）。 */
    public static final String ENTITY_TYPE = "EntityType";

    /** 物品根 Tag 下：收纳时间戳（{@code Level#getGameTime()}）。仅用于展示与排查，不参与逻辑。 */
    public static final String STORED_AT = "StoredAt";

    /**
     * {@link #ROOT} 内：实体主人 UUID。
     *
     * <p>注意这是 {@code LivingEntity} 自己的 {@code Owner} 字段（原版狼/猫与
     * {@code TamableAnimal} 都写这个键），不是本模组另加的键，因此直接复用原版语义。
     */
    public static final String OWNER = "Owner";

    /**
     * 收纳体积上限（字节）。超过则拒绝收纳。
     *
     * <p>Forge 单包载荷上限为 1,048,576 字节（{@code VanillaPacketSplitter}），超出后走分片通道
     * 拆包发送 —— 不会踢客户端，但每次物品栏同步都要发几十个大包并重新压缩，是中大型服务器上
     * 实打实的性能问题。所以这是<b>性能红线，不是崩溃红线</b>，宁可在收纳瞬间拒绝。
     *
     * <p>实测参考：女仆 21 格背包全塞潜影盒约 40 KB，本阈值留了 6 倍余量。
     */
    public static final int MAX_DATA_SIZE = 256 * 1024;

    private StorageToolNbt() {
    }

    /** 该物品是否已装载实体数据。 */
    public static boolean hasEntityData(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(ROOT, CompoundTag.TAG_COMPOUND);
    }

    /**
     * 读取已装载的实体数据；未装载时返回 {@code null}。
     *
     * <p>调用方必须判空 —— 空工具被误送进释放分支时，这里就是唯一的护栏。
     */
    @Nullable
    public static CompoundTag getEntityData(ItemStack stack) {
        return hasEntityData(stack) ? stack.getTagElement(ROOT) : null;
    }
}
