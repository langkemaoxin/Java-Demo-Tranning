package org.example.java8Demo;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
interface MyFunctionalInterface {
    void doSomething(String message); // 只有一个抽象方法

    // 默认方法
    default void defaultMethod() {
        System.out.println("This is a default method.");
    }

    // 静态方法
    static void staticMethod() {
        System.out.println("This is a static method.");
    }
}

public class ComprehensiveDemo {
    public static void main(String[] args) {
        // 1. 自定义函数式接口 + Lambda 表达式
        MyFunctionalInterface myFunc = message -> System.out.println("Custom Functional Interface: " + message);
        myFunc.doSomething("Hello, World!");

        // 调用默认方法
        myFunc.defaultMethod();

        // 调用静态方法
        MyFunctionalInterface.staticMethod();

        // 2. 使用内置函数式接口

        // Consumer: 接受一个参数，无返回值
        Consumer<String> printMessage = message -> System.out.println("Consumer: " + message);
        printMessage.accept("This is a message from Consumer.");

        // Supplier: 无参数，返回一个值
        Supplier<Double> randomValue = () -> Math.random();
        System.out.println("Supplier: Random value = " + randomValue.get());

        // Function: 接受一个参数，返回一个值
        Function<String, Integer> stringLength = s -> s.length();
        System.out.println("Function: Length of 'Hello' = " + stringLength.apply("Hello"));

        // Predicate: 接受一个参数，返回布尔值
        Predicate<String> isLong = s -> s.length() > 5;
        System.out.println("Predicate: Is 'HelloWorld' long? " + isLong.test("HelloWorld"));
        System.out.println("Predicate: Is 'Hi' long? " + isLong.test("Hi"));

        // 3. 综合应用：结合 Predicate 和 Consumer
        Predicate<String> isEmpty = s -> s == null || s.trim().isEmpty();
        Consumer<String> handleEmptyText = text -> {
            if (isEmpty.test(text)) {
                System.out.println("Text is empty!");
            } else {
                System.out.println("Text is not empty: " + text);
            }
        };

        handleEmptyText.accept("");       // 空文本
        handleEmptyText.accept("Hello");  // 非空文本
    }
}
