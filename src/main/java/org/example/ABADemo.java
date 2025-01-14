package org.example;

import java.util.concurrent.atomic.AtomicReference;

public class ABADemo {

    private static AtomicReference<String> ref = new AtomicReference<>("A");

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            String value = ref.get();
            System.out.println("Thread 1 reads: " + value); // A
            // 模拟线程 1 被挂起
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 尝试将 A 改为 C
            boolean success = ref.compareAndSet(value, "C");
            System.out.println("Thread 1 CAS result: " + success); // true
        });

        Thread thread2 = new Thread(() -> {
            // 将 A 改为 B
            ref.compareAndSet("A", "B");
            System.out.println("Thread 2 changes A to B");
            // 将 B 改回 A
            ref.compareAndSet("B", "A");
            System.out.println("Thread 2 changes B back to A");
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
