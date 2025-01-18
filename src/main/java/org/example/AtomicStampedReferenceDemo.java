package org.example;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        // 初始值为 "A"，版本号为 0
        AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

        // 线程 1：尝试将 "A" 改为 "B"
        Thread thread1 = new Thread(() -> {
            int[] stampHolder = new int[1];
            String currentRef = ref.get(stampHolder); // 获取当前值和版本号
            int currentStamp = stampHolder[0];

            System.out.println("Thread 1: Current value = " + currentRef + ", stamp = " + currentStamp);

            // 尝试将 "A" 改为 "B"，版本号从 0 改为 1
            boolean success = ref.compareAndSet(currentRef, "B", currentStamp, currentStamp + 1);
            System.out.println("Thread 1: CAS result = " + success);
        });

        // 线程 2：先将 "A" 改为 "B"，再改回 "A"
        Thread thread2 = new Thread(() -> {
            int[] stampHolder = new int[1];
            String currentRef = ref.get(stampHolder); // 获取当前值和版本号
            int currentStamp = stampHolder[0];

            System.out.println("Thread 2: Current value = " + currentRef + ", stamp = " + currentStamp);

            // 先将 "A" 改为 "B"，版本号从 0 改为 1
            boolean success1 = ref.compareAndSet(currentRef, "B", currentStamp, currentStamp + 1);
            System.out.println("Thread 2: First CAS result = " + success1);

            // 获取更新后的值和版本号
            currentRef = ref.get(stampHolder);
            currentStamp = stampHolder[0];
            System.out.println("Thread 2: After first CAS, value = " + currentRef + ", stamp = " + currentStamp);

            // 再将 "B" 改回 "A"，版本号从 1 改为 2
            boolean success2 = ref.compareAndSet(currentRef, "A", currentStamp, currentStamp + 1);
            System.out.println("Thread 2: Second CAS result = " + success2);

            // 获取最终的值和版本号
            currentRef = ref.get(stampHolder);
            currentStamp = stampHolder[0];
            System.out.println("Thread 2: After second CAS, value = " + currentRef + ", stamp = " + currentStamp);
        });

        // 启动线程
        thread1.start();
        thread2.start();

        // 等待线程执行完毕
        thread1.join();
        thread2.join();

        // 打印最终的值和版本号
        int[] stampHolder = new int[1];
        String finalValue = ref.get(stampHolder);
        int finalStamp = stampHolder[0];
        System.out.println("Final value = " + finalValue + ", final stamp = " + finalStamp);
    }
}