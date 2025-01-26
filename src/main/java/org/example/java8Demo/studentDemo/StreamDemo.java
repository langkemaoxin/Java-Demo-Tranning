package org.example.java8Demo.studentDemo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;


public class StreamDemo {
    public static void main(String[] args) {
        // 初始化学生列表
        List<Student> students = Arrays.asList(
                new Student(1, "Alice", 20, "Female", 85.5),
                new Student(2, "Bob", 21, "Male", 72.0),
                new Student(3, "Charlie", 22, "Male", 91.0),
                new Student(4, "Diana", 20, "Female", 88.0),
                new Student(5, "Eva", 21, "Female", 65.5),
                new Student(6, "Frank", 22, "Male", 59.0)
        );

        // 1. 过滤出成绩大于 80 的学生
        List<Student> highScoreStudents = students.stream()
                .filter(s -> s.getScore() > 80)
                .collect(Collectors.toList());
        System.out.println("成绩大于 80 的学生：");
        highScoreStudents.forEach(System.out::println);

        // 2. 按成绩从高到低排序
        List<Student> sortedStudents = students.stream()
                .sorted(Comparator.comparingDouble(Student::getScore).reversed())
                .collect(Collectors.toList());
        System.out.println("\n按成绩从高到低排序：");
        sortedStudents.forEach(System.out::println);

        // 3. 提取所有学生的姓名
        List<String> studentNames = students.stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println("\n所有学生的姓名：");
        studentNames.forEach(System.out::println);

        // 4. 统计男女生的人数
        Map<String, Long> genderCount = students.stream()
                .collect(Collectors.groupingBy(Student::getGender, Collectors.counting()));
        System.out.println("\n男女生的人数：");
        genderCount.forEach((gender, count) -> System.out.println(gender + ": " + count));

        // 5. 计算所有学生的平均成绩
        double averageScore = students.stream()
                .mapToDouble(Student::getScore)
                .average()
                .orElse(0.0);
        System.out.println("\n所有学生的平均成绩：" + averageScore);

        // 6. 将学生按成绩分组
        Map<String, List<Student>> scoreGroup = students.stream()
                .collect(Collectors.groupingBy(s -> {
                    if (s.getScore() >= 90) return "优秀";
                    else if (s.getScore() >= 80) return "良好";
                    else if (s.getScore() >= 60) return "及格";
                    else return "不及格";
                }));
        System.out.println("\n按成绩分组：");
        scoreGroup.forEach((group, list) -> {
            System.out.println(group + ":");
            list.forEach(System.out::println);
        });
    }
}
