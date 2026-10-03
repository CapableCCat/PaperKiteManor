"""精简后的收口自检脚本。

检查项刻意围绕 F1/F2/F3 暴露出的风险面：
- 关键不变量是否还在（换符必须转移 NBT、Scope 语义、写入/读取层级一致）
- 临时诊断代码是否清理干净
- 是否留下未使用的 import
- 日志是否只出现在错误路径
"""

import re
from pathlib import Path

BASE = Path(r"F:\Project\Public Project\PaperKiteManor")
ITEM = BASE / "src/main/java/com/kazi_cat/papercraft_magic_decoration/item/StorageToolItem.java"
NBT = BASE / "src/main/java/com/kazi_cat/papercraft_magic_decoration/item/StorageToolNbt.java"
EVENT = BASE / "src/main/java/com/kazi_cat/papercraft_magic_decoration/event/RightClickEvent.java"

results: list[tuple[bool, str]] = []


def check(ok: bool, label: str, detail: str = "") -> None:
    results.append((ok, f"{label}{(' —— ' + detail) if detail else ''}"))


def main() -> None:
    item = ITEM.read_text(encoding="utf-8")
    nbt = NBT.read_text(encoding="utf-8")
    event = EVENT.read_text(encoding="utf-8")

    # ---------- 1. F2 的不变量：换符必须显式转移 NBT ----------
    check("full.setTag(" in item, "F2 不变量：swapToFullTool 仍在显式转移 NBT")
    check("data.copy()" in item, "F2 不变量：NBT 用 copy() 而非传引用",
          "避免两个 ItemStack 共享同一 CompoundTag")

    # ---------- 2. F3 的契约：写入与读取都必须在 EntityData 内部 ----------
    write_in_root = re.search(r"itemTag\.putString\(\s*StorageToolNbt\.ENTITY_TYPE", item)
    check(write_in_root is None, "F3 契约：ENTITY_TYPE 未被写到物品根",
          "若命中说明写入位置又退回根键")
    check(re.search(r"data\.putString\(\s*StorageToolNbt\.ENTITY_TYPE", item) is not None,
          "F3 契约：ENTITY_TYPE 写入 data（EntityData 内部）")
    check(re.search(r"data\.getString\(\s*StorageToolNbt\.ENTITY_TYPE", nbt) is None
          and re.search(r"data\.getString\(\s*StorageToolNbt\.ENTITY_TYPE", item) is not None,
          "F3 契约：读取从 EntityData 内部取",
          "写入与读取必须同层")

    # ---------- 3. F1 的语义：空符必须能收纳，满符必须收敛为拒绝 ----------
    check("if (scope != null) {" in item and "请先在物品栏里切回空符" in item,
          "F1 语义：满符收敛为拒绝并提示切回空符")
    check("Scope.forEntity" in item, "F1 语义：归属范围由实体决定（forEntity 存在）")

    # ---------- 4. 临时诊断代码清理 ----------
    # 只查「字符串字面量」里的诊断标记，不查注释 —— 注释里提到「诊断」是在解释设计，属正常
    for path, name in ((ITEM, "StorageToolItem"), (EVENT, "RightClickEvent")):
        text = path.read_text(encoding="utf-8")
        check("[收纳工具/诊断" not in text, f"{name} 已无诊断日志字面量")
        check("LOGGER.info" not in text, f"{name} 无 INFO 级日志（正常路径应静默）")

    # ---------- 5. 日志只用于错误路径 ----------
    log_calls = re.findall(r"LOGGER\.(info|warn|error)", item + event)
    check(all(lvl in ("warn", "error") for lvl in log_calls),
          "全部日志均为 warn/error 级", f"共 {len(log_calls)} 处")

    # ---------- 6. 未使用的 import（粗查：import 的简单类名是否在正文出现）----------
    for path, name in ((ITEM, "StorageToolItem"), (EVENT, "RightClickEvent"), (NBT, "StorageToolNbt")):
        text = path.read_text(encoding="utf-8")
        body = text.split("\n")
        imports = [l for l in body if l.startswith("import ")]
        rest = "\n".join(l for l in body if not l.startswith("import "))
        unused = []
        for imp in imports:
            cls = imp.rstrip(";").split(".")[-1]
            if cls == "*":
                continue
            if not re.search(rf"\b{re.escape(cls)}\b", rest):
                unused.append(cls)
        check(not unused, f"{name} 无未使用的 import", f"可疑：{unused}" if unused else "")

    # ---------- 7. 关键防御分支仍在 ----------
    check("type == null" in item, "防崩服：EntityType 查不到时的分支仍在")
    check("spawned == null" in item, "防崩服：type.create 返回 null 的分支仍在")
    check("level.getEntity(entity.getUUID()) != null" in item, "保险 1：UUID 冲突重生成仍在")
    check("entity.setUUID(UUID.randomUUID())" in item, "保险 1：冲突时确实重新生成 UUID")
    check("closeOpenContainer" in item and "closeOpenContainer" in event,
          "容器隔空取物防线仍在（定义 + 调用）")

    # ---------- 输出 ----------
    failed = [d for ok, d in results if not ok]
    for ok, d in results:
        print(("  OK   " if ok else "  FAIL ") + d)
    print()
    print(f"共 {len(results)} 项，失败 {len(failed)} 项")
    if failed:
        print("FAILED:")
        for d in failed:
            print("  - " + d)
    raise SystemExit(1 if failed else 0)


if __name__ == "__main__":
    main()
