package org.example.jol.concurrent;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.*;

public class LongAdderVsAtomicLong {

    public static void main(String[] args) throws InterruptedException {
        int[] threadCounts = {50}; // 线程数
        int[] incrementsPerThread = {100,1000*10,1000*100, 1000*1000, 1000*10000,1000*1000000}; // 每个线程的递增次数

        for (int threads : threadCounts) {
            for (int increments : incrementsPerThread) {
                System.out.println("===== 测试配置: 线程数 = " + threads + ", 每个线程递增次数 = " + increments + " =====");

                // 测试 AtomicLong
                AtomicLong atomicLong = new AtomicLong(0);
                long atomicTime = testAtomicLong(atomicLong, threads, increments);
                System.out.println("AtomicLong 总执行时间: " + atomicTime + " ms");

                // 测试 LongAdder
                LongAdder longAdder = new LongAdder();
                long adderTime = testLongAdder(longAdder, threads, increments);
                System.out.println("LongAdder 总执行时间: " + adderTime + " ms");

                // 计算吞吐量
                double atomicThroughput = (threads * increments) / (atomicTime / 1000.0);
                double adderThroughput = (threads * increments) / (adderTime / 1000.0);
                System.out.println("AtomicLong 吞吐量: " + atomicThroughput + " 操作/秒");
                System.out.println("LongAdder 吞吐量: " + adderThroughput + " 操作/秒");
                System.out.println();
            }
        }
    }

    /**
     * 测试 AtomicLong 的性能
     */
    private static long testAtomicLong(AtomicLong atomicLong, int threadCount, int incrementsPerThread) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    atomicLong.incrementAndGet();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        return System.currentTimeMillis() - startTime;
    }

    /**
     * 测试 LongAdder 的性能
     */
    private static long testLongAdder(LongAdder longAdder, int threadCount, int incrementsPerThread) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    longAdder.increment();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        return System.currentTimeMillis() - startTime;
    }
}