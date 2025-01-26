package org.example.java8Demo.studentDemo;

public class Student {
    private int id;
    private String name;
    private int age;
    private String gender;
    private double score;

    public Student(int id, String name, int age, String gender, double score) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.score = score;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public double getScore() { return score; }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", score=" + score +
                '}';
    }
}
