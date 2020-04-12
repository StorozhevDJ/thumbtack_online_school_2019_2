package com.lineate.threads;

import java.util.ArrayList;
import java.util.List;

enum Operation {
    WRITE,
    READ
}


class MyClass {
    private final List<Integer> integerList;

    MyClass(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public synchronized void write(int number) {
        integerList.add(number);
        System.out.println("Add = " + number);
    }

    public synchronized void read(int index) {
        integerList.remove(index);
        System.out.println("Delete = " + index);
    }

    public Boolean isEmpty() {
        return integerList.isEmpty();
    }

    public Integer size() {
        return integerList.size();
    }
}


class MyThread5 extends Thread {
    private final MyClass myClass;
    private final Operation op;

    public MyThread5(MyClass myClass, Operation op) {
        this.myClass = myClass;
        this.op = op;
    }

    @Override
    public void run() {

        System.out.println("Operation " + op);
        if (op == Operation.READ) {
            System.out.println("Start delete... ");
            int count = 0;
            while (count <= 10000) {
                if (!myClass.isEmpty()) {
                    int ptr = (int) (Math.random() * (myClass.size() - 1));
                    myClass.read(ptr);
                    count++;
                }
            }
        } else {
            System.out.println("Start add... ");
            for (int i = 0; i < 10000; i++) {
                int rand = (int) (Math.random() * 1000);
                myClass.write(rand);
            }
            System.out.println("end add ");
        }
        System.out.println("End " + op);
    }
}

public class Task5 {
    /**
     * То же самое, но оба потока на базе одного и того же класса, использовать synchronized методы.
     * В конструктор класса потока передается параметр типа enum, указывающий, что должен делать этот поток.
     */
    public static void main(String args[]) {
        List<Integer> integerList = new ArrayList<>();
        MyClass myClass = new MyClass(integerList);

        MyThread5 threadPlus = new MyThread5(myClass, Operation.WRITE);
        MyThread5 threadMinus = new MyThread5(myClass, Operation.READ);

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
