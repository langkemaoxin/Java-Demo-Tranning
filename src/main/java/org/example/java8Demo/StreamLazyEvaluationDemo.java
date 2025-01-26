package org.example.java8Demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamLazyEvaluationDemo {
    public static void main(String[] args) {
        // 初始化一个整数列表
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // 创建一个 Stream，并添加中间操作
        List<Integer> result = numbers.stream()
                .filter(n -> {
                    System.out.println("过滤偶数: " + n);
                    return n % 2 == 0; // 过滤出偶数
                })
                .map(n -> {
                    System.out.println("将偶数乘以 2: " + n);
                    return n * 2; // 将偶数乘以 2
                })
                .toList(); // 触发终端操作

        // 打印最终结果
        System.out.println("最终结果: " + result);
    }
}
