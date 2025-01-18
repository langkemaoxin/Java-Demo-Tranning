package org.example.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeCASExample{
    private static Unsafe unsafe;
    private static final long valueOffset; // 字段的内存偏移量

    private volatile int value; // 需要更新的字段

    static {
        try {
            // 获取 Unsafe 实例
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            unsafe = (Unsafe) theUnsafeField.get(null);

            // 获取字段的内存偏移量
            valueOffset = unsafe.objectFieldOffset(UnsafeCASExample.class.getDeclaredField("value"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Unsafe", e);
        }
    }

    // 使用 CAS 更新 value 字段
    public boolean compareAndSet(int expected, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expected, update);
    }

    public static void main(String[] args) {
        UnsafeCASExample example = new UnsafeCASExample();

        // 初始值
        example.value = 10;
        System.out.println("Initial value: " + example.value);

        // 使用 CAS 更新值
        boolean success = example.compareAndSet(10, 20);
        System.out.println("CAS update result: " + success);
        System.out.println("Updated value: " + example.value);

        // 尝试更新失败（期望值不匹配）
        success = example.compareAndSet(10, 30);
        System.out.println("CAS update result: " + success);
        System.out.println("Value after failed update: " + example.value);
    }
}