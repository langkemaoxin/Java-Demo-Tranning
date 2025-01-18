package org.example.jol;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterExample {

    // 定义一个包含 volatile int 字段的类
    public static class Counter {
        private volatile int count;

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) {
        // 创建 AtomicIntegerFieldUpdater 实例
        AtomicIntegerFieldUpdater<Counter> updater =
                AtomicIntegerFieldUpdater.newUpdater(Counter.class, "count");

        // 创建一个 Counter 实例
        Counter counter = new Counter();

        // 使用 updater 对 count 字段进行原子操作
        updater.set(counter, 10);  // 设置 count 的值为 10
        System.out.println("Initial count: " + counter.getCount());

        updater.incrementAndGet(counter);  // 原子地增加 count 的值
        System.out.println("After increment: " + counter.getCount());

        updater.addAndGet(counter, 5);  // 原子地增加 count 的值 5
        System.out.println("After adding 5: " + counter.getCount());

        boolean updated = updater.compareAndSet(counter, 16, 20);  // 如果当前值是 16，则设置为 20
        System.out.println("CAS operation result: " + updated);
        System.out.println("Final count: " + counter.getCount());
    }
}