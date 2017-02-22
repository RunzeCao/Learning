package com.example;

public class Demo {
    public static void main(String[] a) {
 /*       SellDemo sellDemo = new SellDemo();
        new Thread(sellDemo, "A").start();
        new Thread(sellDemo, "B").start();
        new Thread(sellDemo, "C").start();*/

        Person p = new Person();
        new Thread(new Producer(p)).start();
        new Thread(new Consumer(p)).start();
    }
}
