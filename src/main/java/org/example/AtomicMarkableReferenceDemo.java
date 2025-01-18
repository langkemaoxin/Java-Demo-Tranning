package org.example;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        // 初始值为 "A"，标记为 false
        AtomicMarkableReference<String> ref = new AtomicMarkableReference<>("A", false);

        // 线程 1：尝试将 "A" 改为 "B"
        Thread thread1 = new Thread(() -> {
            boolean[] markHolder = new boolean[1];
            String currentRef = ref.get(markHolder); // 获取当前值和标记
            boolean currentMark = markHolder[0];

            System.out.println("Thread 1: Current value = " + currentRef + ", mark = " + currentMark);

            // 尝试将 "A" 改为 "B"，标记从 false 改为 true
            boolean success = ref.compareAndSet(currentRef, "B", currentMark, !currentMark);
            System.out.println("Thread 1: CAS result = " + success);

            // 获取更新后的值和标记
            currentRef = ref.get(markHolder);
            currentMark = markHolder[0];
            System.out.println("Thread 1: After CAS, value = " + currentRef + ", mark = " + currentMark);
        });

        // 线程 2：先将 "A" 改为 "B"，再改回 "A"
        Thread thread2 = new Thread(() -> {
            boolean[] markHolder = new boolean[1];
            String currentRef = ref.get(markHolder); // 获取当前值和标记
            boolean currentMark = markHolder[0];

            System.out.println("Thread 2: Current value = " + currentRef + ", mark = " + currentMark);

            // 先将 "A" 改为 "B"，标记从 false 改为 true
            boolean success1 = ref.compareAndSet(currentRef, "B", currentMark, !currentMark);
            System.out.println("Thread 2: First CAS result = " + success1);

            // 获取更新后的值和标记
            currentRef = ref.get(markHolder);
            currentMark = markHolder[0];
            System.out.println("Thread 2: After first CAS, value = " + currentRef + ", mark = " + currentMark);

            // 再将 "B" 改回 "A"，标记从 true 改为 false
            boolean success2 = ref.compareAndSet(currentRef, "A", currentMark, !currentMark);
            System.out.println("Thread 2: Second CAS result = " + success2);

            // 获取最终的值和标记
            currentRef = ref.get(markHolder);
            currentMark = markHolder[0];
            System.out.println("Thread 2: After second CAS, value = " + currentRef + ", mark = " + currentMark);
        });

        // 启动线程
        thread1.start();
        thread2.start();

        // 等待线程执行完毕
        thread1.join();
        thread2.join();

        // 打印最终的值和标记
        boolean[] markHolder = new boolean[1];
        String finalValue = ref.get(markHolder);
        boolean finalMark = markHolder[0];
        System.out.println("Final value = " + finalValue + ", final mark = " + finalMark);
    }
}