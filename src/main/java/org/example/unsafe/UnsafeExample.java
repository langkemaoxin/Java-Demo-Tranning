package org.example.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeExample {
    public static void main(String[] args) throws Exception {
        // 获取 Unsafe 类的实例
        Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeField.setAccessible(true); // 绕过访问限制
        Unsafe unsafe = (Unsafe) theUnsafeField.get(null);

        // 使用 Unsafe
        System.out.println("Unsafe instance: " + unsafe);


        Unsafe unsafe1 = Unsafe.getUnsafe();

    }
}