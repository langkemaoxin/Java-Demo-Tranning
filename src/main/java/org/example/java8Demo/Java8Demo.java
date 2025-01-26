package org.example.java8Demo;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.Period;

public class Java8Demo {

    public static void main(String[] args) {
        // Lambda 表达式
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        names.forEach(name -> System.out.println(name));

        // Stream API
        List<String> filteredNames = names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());
        System.out.println("Names starting with 'A': " + filteredNames);

        // 默认方法
        MyInterface myInterface = new MyInterfaceImpl();
        myInterface.defaultMethod();

        // Optional 类
        Optional<String> optionalName = Optional.ofNullable(names.get(0));
        optionalName.ifPresent(name -> System.out.println("Name is present: " + name));

        // 新的日期和时间 API
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(1990, 1, 1);
        Period age = Period.between(birthday, today);
        System.out.println("Age: " + age.getYears() + " years");
    }
}

interface MyInterface {
    default void defaultMethod() {
        System.out.println("This is a default method in the interface.");
    }
}

class MyInterfaceImpl implements MyInterface {
    // 可以覆盖默认方法
    @Override
    public void defaultMethod() {
        System.out.println("This is the overridden default method in the implementation class.");
    }
}