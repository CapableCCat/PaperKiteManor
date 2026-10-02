# 项目长期约束

- 项目文件默认仅写入 `F:\Project\Public Project\PaperKiteManor`，目录外编辑前必须先向用户确认并获得同意。
- 本机代理：优先使用 `localhost:7897`（Clash Verge）；不可用时先向用户说明，由用户开启备用 `localhost:7890`（Clash）。
- 技术栈：Minecraft 1.20.1 + Forge 47.3.0 + GeckoLib4；Java 17；Gradle 8.8（项目自带 wrapper）；Parchment 映射 `2023.08.20-1.20.1`。
- **命名要害**：`mod_id` = `papercraft_magic_decoration`，`mod_name` = `Paper Kite Manor`（纸鸢庄园）。**命名空间、资源路径、lang key、注册名一律用 mod_id**，`Paper Kite Manor` 只是显示名。
- 项目文档统一带「纸鸢庄园 · 」前缀，文档地图与唯一事实源约定见 `docs/纸鸢庄园 · 项目文档索引.md`。
- 模组界面与物品名使用中文（`zh_cn.json` 为准），英文同步维护 `en_us.json`。
- 开发环境需要图形界面才能跑 `runClient`；编译与 datagen（`build` / `runData`）可无头执行，可加 `--offline` 加速。
- 本仓库**不使用** `.workbuddy/` 记忆目录（避免与 WorkBuddy 工具的记忆体系冲突）；跨会话记忆靠 `docs/纸鸢庄园 · 新对话交接提示词（通用版）.md` 的「交接约定」手动维护。
