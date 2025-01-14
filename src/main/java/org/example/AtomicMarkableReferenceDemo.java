package org.example;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceDemo {

    public static void main(String[] args) {
        // 初始值为 "A"，标记为 false
        AtomicMarkableReference<String> ref = new AtomicMarkableReference<>("A", false);

        // 线程 1：尝试将 "A" 改为 "B"
        new Thread(() -> {
            boolean[] markHolder = new boolean[1];
            String currentRef = ref.get(markHolder); // 获取当前值和标记
            boolean currentMark = markHolder[0];

            System.out.println("Thread 1: Current value = " + currentRef + ", mark = " + currentMark);

            // 尝试将 "A" 改为 "B"，标记从 false 改为 true
            boolean success = ref.compareAndSet(currentRef, "B", currentMark, !currentMark);
            System.out.println("Thread 1: CAS result = " + success);
        }).start();

        // 线程 2：先将 "A" 改为 "B"，再改回 "A"
        new Thread(() -> {
            boolean[] markHolder = new boolean[1];
            String currentRef = ref.get(markHolder); // 获取当前值和标记
            boolean currentMark = markHolder[0];

            System.out.println("Thread 2: Current value = " + currentRef + ", mark = " + currentMark);

            // 先将 "A" 改为 "B"，标记从 false 改为 true
            ref.compareAndSet(currentRef, "B", currentMark, !currentMark);

            // 再将 "B" 改回 "A"，标记从 true 改为 false
            currentRef = ref.get(markHolder);
            currentMark = markHolder[0];
            ref.compareAndSet(currentRef, "A", currentMark, !currentMark);
        }).start();
    }
}