"""生成「收纳工具」的占位材质（16x16 像素画，程序绘制）。

存在意义：在用户画出正式材质之前，让游戏内能正常显示物品，而不是紫黑缺失方块。
三张图刻意用不同主色区分空 / 纸鸢 / 原版，方便肉眼分辨状态。

图案是一张「符纸」：深色描边 + 纸色内层 + 中央竖向色带 + 上下两道横纹。
坐标约定：x 向右、y 向下，(0,0) 为左上角。
"""

from pathlib import Path

from PIL import Image

SIZE = 16
OUT_DIR = Path(
    r"F:\Project\Public Project\PaperKiteManor\src\main\resources"
    r"\assets\papercraft_magic_decoration\textures\item"
)

# (文件名, 描边色, 纸面色, 中央色带色)
PALETTES = [
    ("storage_tool.png", (74, 62, 48), (232, 219, 190), (176, 150, 106)),
    ("storage_tool_manor.png", (30, 62, 74), (206, 233, 236), (58, 140, 160)),
    ("storage_tool_vanilla.png", (74, 56, 26), (243, 228, 190), (196, 146, 54)),
]

# 符纸轮廓：留出边距的圆角矩形
LEFT, RIGHT, TOP, BOTTOM = 2, 13, 1, 14


def draw_talisman(stroke: tuple[int, int, int],
                  paper: tuple[int, int, int],
                  accent: tuple[int, int, int]) -> Image.Image:
    img = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    px = img.load()

    for y in range(TOP, BOTTOM + 1):
        for x in range(LEFT, RIGHT + 1):
            # 切掉四个角，做出符纸的圆角轮廓
            if x in (LEFT, RIGHT) and y in (TOP, BOTTOM):
                continue
            is_edge = x in (LEFT, RIGHT) or y in (TOP, BOTTOM)
            px[x, y] = (stroke if is_edge else paper) + (255,)

    # 中央竖向色带（宽 2px），象征符上的纹样
    for y in range(TOP + 2, BOTTOM - 1):
        px[7, y] = accent + (255,)
        px[8, y] = accent + (255,)

    # 上下两道横纹
    for x in range(LEFT + 3, RIGHT - 2):
        px[x, TOP + 4] = stroke + (255,)
        px[x, BOTTOM - 4] = stroke + (255,)

    return img


def main() -> None:
    OUT_DIR.mkdir(parents=True, exist_ok=True)
    for name, stroke, paper, accent in PALETTES:
        path = OUT_DIR / name
        draw_talisman(stroke, paper, accent).save(path)
        print(f"{path.name}\t{path.stat().st_size} bytes")


if __name__ == "__main__":
    main()
