package org.example.java8Demo;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferenceDemo {
    public static void main(String[] args) {
        // 1. 静态方法引用
        System.out.println("=== 静态方法引用 ===");
        // 使用 Lambda 表达式
        Function<String, Integer> lambda1 = s -> Integer.parseInt(s);
        System.out.println("Lambda: " + lambda1.apply("123"));

        // 使用静态方法引用
        Function<String, Integer> methodRef1 = Integer::parseInt;
        System.out.println("Method Reference: " + methodRef1.apply("456"));

        // 2. 实例方法引用
        System.out.println("\n=== 实例方法引用 ===");
        // 创建一个对象
        Printer printer = new Printer();

        // 使用 Lambda 表达式
        Consumer<String> lambda2 = s -> printer.print(s);
        lambda2.accept("Hello, Lambda!");

        // 使用实例方法引用
        Consumer<String> methodRef2 = printer::print;
        methodRef2.accept("Hello, Method Reference!");

        // 3. 类的任意对象的实例方法引用
        System.out.println("\n=== 类的任意对象的实例方法引用 ===");
        // 使用 Lambda 表达式
        Function<String, String> lambda3 = s -> s.toUpperCase();
        System.out.println("Lambda: " + lambda3.apply("hello"));

        // 使用类的任意对象的实例方法引用
        Function<String, String> methodRef3 = String::toUpperCase;
        System.out.println("Method Reference: " + methodRef3.apply("world"));

        // 4. 构造函数引用
        System.out.println("\n=== 构造函数引用 ===");
        // 使用 Lambda 表达式
        Supplier<StringBuilder> lambda4 = () -> new StringBuilder();
        System.out.println("Lambda: " + lambda4.get().append("Hello"));

        // 使用构造函数引用
        Supplier<StringBuilder> methodRef4 = StringBuilder::new;
        System.out.println("Method Reference: " + methodRef4.get().append("World"));
    }
}

// 用于实例方法引用的类
class Printer {
    public void print(String message) {
        System.out.println("Printing: " + message);
    }
}