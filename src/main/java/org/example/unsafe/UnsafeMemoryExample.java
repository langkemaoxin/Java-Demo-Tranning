package org.example.unsafe;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeMemoryExample {
    public static void main(String[] args) throws Exception {
        // 获取 Unsafe 实例
        Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafeField.get(null);

        // 分配 100 字节的内存
        long address = unsafe.allocateMemory(100);
        System.out.println("Allocated memory at address: " + address);

        // 在内存中写入数据
        unsafe.putInt(address, 42);
        System.out.println("Value at address: " + unsafe.getInt(address));

        // 释放内存
        unsafe.freeMemory(address);
        System.out.println("Memory freed.");
    }
}