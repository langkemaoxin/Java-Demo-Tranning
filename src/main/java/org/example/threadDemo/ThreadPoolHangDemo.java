package org.example.threadDemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 为什么程序会挂起？
 *
 * 非守护线程：线程池中的线程默认是非守护线程（non-daemon threads）。
 *
 * JVM 退出条件：JVM 只有在所有非守护线程都执行完毕后才会退出。
 *
 * 线程池未关闭：如果没有调用 shutdown() 或 shutdownNow()，线程池中的线程会一直保持运行状态，即使没有任务需要执行。
 *
 *
 */
public class ThreadPoolHangDemo {

    public static void main(String[] args) {
        // 创建一个固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // 提交任务
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    Thread.sleep(5000); // 模拟任务执行时间（5秒）
                    System.out.println("任务执行完毕: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // 主线程执行完毕
        System.out.println("主线程执行完毕");

        // 注意：这里没有调用 executor.shutdown() 或 executor.shutdownNow()
    }
}

