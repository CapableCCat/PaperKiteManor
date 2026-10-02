package com.kazi_cat.papercraft_magic_decoration.inventory.container;

import net.minecraft.world.entity.Entity;

/**
 * 由「某个实体提供的容器」实现：暴露它背后持有的那个实体。
 *
 * <p><b>为什么需要这个接口</b>：本模组的容器（{@code BunnySuitcaseContainer}、
 * {@code LobbyBoyBackpackContainer}）都持有实体引用，但暴露方式不统一 —— 前者有
 * {@code protected final entity} 字段，后者只在内部 {@code TradeSlot} 上持有。
 * 收纳工具需要在收纳前判断「玩家当前开着的界面是不是这个实体的」，没有统一入口就只能
 * 逐个类型做 {@code instanceof} 转换。
 *
 * <p><b>它解决什么问题</b>：原版 {@code AbstractContainerMenu#stillValid} 通常只判
 * 「实体还活着」。而实体被 {@code discard()} 之后 {@code isAlive()} 仍然为真，
 * 于是容器不会失效 —— 玩家就能「先打开女仆背包 → 再把她收进收纳工具 → 继续从已经
 * 不存在的女仆身上掏东西」。有了本接口，收纳前即可精确关掉「正是这个实体」的界面。
 *
 * <p>刻意只暴露一个方法：容器的其他内部结构（槽位、等级、交易列表）不应被外部依赖。
 */
public interface EntityBoundMenu {
    /** 该容器所服务的实体。实现方必须返回非 {@code null}。 */
    Entity getMenuEntity();
}
