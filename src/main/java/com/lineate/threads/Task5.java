package com.lineate.threads;

import java.util.ArrayList;
import java.util.List;

enum Operation {
    WRITE,
    READ
}

class MyThread5 extends Thread {
    List<Integer> integerList;
    Operation op;

    public MyThread5(List<Integer> integerList, Operation op) {
        this.integerList = integerList;
        this.op = op;
    }

    synchronized void read(int ptr) {
        integerList.remove(ptr);
    }

    synchronized void write(int val) {
        integerList.add(val);
    }

    public void run() {
        int rand;
        int val;
        int ptr;
        for (int i = 0; i < 10000; i++) {
            if (op == Operation.WRITE) {
                rand = (int) (Math.random() * 1000);
                write(rand);
                System.out.println("Added " + rand);
            } else if (op == Operation.READ) {
                if (integerList.size() > 0) {
                    ptr = (int) (Math.random() * (integerList.size() - 1));
                    val = integerList.get(ptr);
                    read(ptr);
                    System.out.println("Removed value " + val + " by ptr " + ptr);
                } else {
                    System.out.println("Empty list ");
                }
            }
        }
    }
}

public class Task5 {
    /**
     * То же самое, но оба потока на базе одного и того же класса, использовать synchronized методы.
     * В конструктор класса потока передается параметр типа enum, указывающий, что должен делать этот поток.
     */
    public static void main(String args[]) {
        List<Integer> integerList = new ArrayList<>();

        MyThread5 threadPlus = new MyThread5(integerList, Operation.WRITE);
        MyThread5 threadMinus = new MyThread5(integerList, Operation.READ);

        threadPlus.start();
        threadMinus.start();

        System.out.println("Thread + is alive: " + threadPlus.isAlive());
        System.out.println("Thread - is alive: " + threadMinus.isAlive());

        try {
            threadPlus.join();
            threadMinus.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
        System.out.println("Thread + is alive: " + threadPlus.isAlive());
        System.out.println("Thread - is alive: " + threadMinus.isAlive());

        System.out.println("Main thread exiting.");
    }
}
