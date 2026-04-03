package com.kazi_cat.papercraft_magic_decoration.utils;

import org.joml.Vector2i;

public class Vector2iCodec {
    // 编码
    public static long encode(int x, int y) {
        return ((long) x << 32) | (y & 0xFFFFFFFFL);
    }

    public static long encode(Vector2i vector2i) {
        return encode(vector2i.x, vector2i.y);
    }

    // 解码 X
    public static int decodeX(long encoded) {
        return (int) (encoded >> 32);
    }

    // 解码 Y
    public static int decodeY(long encoded) {
        return (int) encoded;
    }

    public static Vector2i decode(long encoded) {
        return new Vector2i(decodeX(encoded), decodeY(encoded));
    }
}
