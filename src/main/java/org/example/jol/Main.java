package org.example.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class Main {
    private int aa=100;
    private int bb=200;
    public static void main(String[] args) {
        Main obj = new Main();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        System.out.println("---------------");
        System.out.println(VM.current().details());
    }
}