package org.example.jol.concurrent;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AtomicIntegerFieldUpdaterExample {

    // 定义一个包含 volatile int 字段的类
    public static class Counter {
        private volatile int count;  // 必须是 volatile 字段

        public int getCount() {
            return count;
        }
    }

    // 使用 AtomicIntegerFieldUpdater 的计数器
    public static class FieldUpdaterCounter {
        private static final AtomicIntegerFieldUpdater<Counter> updater =
                AtomicIntegerFieldUpdater.newUpdater(Counter.class, "count");

        private final Counter counter = new Counter();

        public void increment() {
            updater.incrementAndGet(counter);
        }

        public int getCount() {
            return counter.getCount();
        }
    }

    // 使用 AtomicInteger 的计数器
    public static class AtomicIntegerCounter {
        private final java.util.concurrent.atomic.AtomicInteger count = new java.util.concurrent.atomic.AtomicInteger(0);

        public void increment() {
            count.incrementAndGet();
        }

        public int getCount() {
            return count.get();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // 使用 AtomicIntegerFieldUpdater 的计数器
        FieldUpdaterCounter fieldUpdaterCounter = new FieldUpdaterCounter();
        for (int i = 0; i < 1000; i++) {
            executor.execute(fieldUpdaterCounter::increment);
        }

        // 使用 AtomicInteger 的计数器
        AtomicIntegerCounter atomicIntegerCounter = new AtomicIntegerCounter();
        for (int i = 0; i < 1000; i++) {
            executor.execute(atomicIntegerCounter::increment);
        }
 
        // 关闭线程池并等待任务完成
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // 输出结果
        System.out.println("FieldUpdaterCounter final count: " + fieldUpdaterCounter.getCount());
        System.out.println("AtomicIntegerCounter final count: " + atomicIntegerCounter.getCount());
    }
}