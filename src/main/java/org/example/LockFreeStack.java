package org.example;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T> {

    // 定义栈的节点
    private static class Node<T> {
        T value; // 节点值
        Node<T> next; // 下一个节点

        Node(T value) {
            this.value = value;
        }
    }

    // 栈顶节点（使用 AtomicReference 实现 CAS）
    private final AtomicReference<Node<T>> top = new AtomicReference<>();

    // 压栈操作
    public void push(T value) {
        Node<T> newNode = new Node<>(value); // 创建新节点
        while (true) {
            Node<T> currentTop = top.get(); // 获取当前栈顶节点
            newNode.next = currentTop; // 将新节点的 next 指向当前栈顶节点
            // 使用 CAS 操作尝试更新栈顶节点
            if (top.compareAndSet(currentTop, newNode)) {
                break; // 更新成功，退出循环
            }
            // 如果 CAS 失败，说明其他线程修改了栈顶节点，重试
        }
    }

    // 弹栈操作
    public T pop() {
        while (true) {
            Node<T> currentTop = top.get(); // 获取当前栈顶节点
            if (currentTop == null) {
                return null; // 栈为空，返回 null
            }
            Node<T> newTop = currentTop.next; // 获取新的栈顶节点（当前栈顶的下一个节点）
            // 使用 CAS 操作尝试更新栈顶节点
            if (top.compareAndSet(currentTop, newTop)) {
                return currentTop.value; // 更新成功，返回弹出的值
            }
            // 如果 CAS 失败，说明其他线程修改了栈顶节点，重试
        }
    }

    // 测试无锁栈
    public static void main(String[] args) throws InterruptedException {
        LockFreeStack<Integer> stack = new LockFreeStack<>();

        // 创建两个线程，分别压栈和弹栈
        Thread pushThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                stack.push(i);
                System.out.println("Pushed: " + i);
            }
        });

        Thread popThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Integer value = stack.pop();
                System.out.println("Popped: " + value);
            }
        });

        // 启动线程
        pushThread.start();
        popThread.start();

        // 等待线程执行完毕
        pushThread.join();
        popThread.join();

        // 最终栈是否为空
        System.out.println("Final stack top: " + stack.pop());
    }
}