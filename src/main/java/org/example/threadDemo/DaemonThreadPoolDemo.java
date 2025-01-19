package org.example.threadDemo;

import java.util.concurrent.*;

/**
 *
 * 默默守护：守护线程在后台默默运行，不直接与用户交互。
 *
 * 生命周期：守护线程的生命周期依赖于非守护线程执行完毕，JVM 会退出程序。
 *
 * 非关键任务：守护线程通常用于执行一些非关键任务，比如垃圾回收、后台监控等。
 *
 */
public class DaemonThreadPoolDemo {

    public static void main(String[] args) {
        // 自定义线程工厂，创建守护线程
        ThreadFactory daemonThreadFactory = r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(false); // 设置为守护线程
            return thread;
        };

        // 使用自定义线程工厂创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(4, daemonThreadFactory);

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

        // 注意：这里没有调用 executor.shutdown()
    }
}