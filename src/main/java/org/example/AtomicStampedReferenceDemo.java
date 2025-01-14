package org.example;

import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampedReferenceDemo {

    public static void main(String[] args) {
        // 初始值为 "A"，版本号为 0
        AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

        // 线程 1：尝试将 "A" 改为 "B"
        new Thread(() -> {
            int[] stampHolder = new int[1];
            String currentRef = ref.get(stampHolder); // 获取当前值和版本号
            int currentStamp = stampHolder[0];

            System.out.println("Thread 1: Current value = " + currentRef + ", stamp = " + currentStamp);

            // 尝试将 "A" 改为 "B"，版本号从 0 改为 1
            boolean success = ref.compareAndSet(currentRef, "B", currentStamp, currentStamp + 1);
            System.out.println("Thread 1: CAS result = " + success);
        }).start();

        // 线程 2：先将 "A" 改为 "B"，再改回 "A"
        new Thread(() -> {
            int[] stampHolder = new int[1];
            String currentRef = ref.get(stampHolder); // 获取当前值和版本号
            int currentStamp = stampHolder[0];

            System.out.println("Thread 2: Current value = " + currentRef + ", stamp = " + currentStamp);

            // 先将 "A" 改为 "B"，版本号从 0 改为 1
            ref.compareAndSet(currentRef, "B", currentStamp, currentStamp + 1);

            // 再将 "B" 改回 "A"，版本号从 1 改为 2
            currentRef = ref.get(stampHolder);
            currentStamp = stampHolder[0];
            ref.compareAndSet(currentRef, "A", currentStamp, currentStamp + 1);
        }).start();
    }
}