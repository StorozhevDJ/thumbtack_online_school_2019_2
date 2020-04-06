package com.lineate.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyThread10Plus extends Thread {
    private List<Integer> integerList;
    private Lock lock;

    public MyThread10Plus(List<Integer> integer, Lock lock) {
        this.integerList = integer;
        this.lock = lock;
    }

    public void run() {
        int rand;
        System.out.println("Thread MyThread10Plus started");
        for (int i = 0; i < 10000; i++) {
            try {
                lock.lock();
                rand = (int) (Math.random() * 1000);
                integerList.add(rand);
                System.out.println("Added " + rand);
            } finally {
                lock.unlock();
            }
        }

    }
}

class MyThread10Minus extends Thread {
    List<Integer> integerList;
    private Lock lock;

    public MyThread10Minus(List<Integer> integerList, Lock lock) {
        this.integerList = integerList;
        this.lock = lock;
    }

    public void run() {
        int val;
        int ptr;
        System.out.println("Thread MyThread10Minus started");
        for (int i = 0; i < 10000; i++) {
            try {
                lock.lock();
                if (integerList.size() != 0) {
                    ptr = (int) (Math.random() * (integerList.size() - 1));
                    val = integerList.get(ptr);
                    integerList.remove(ptr);
                    System.out.println("Removed value " + val + " by ptr " + ptr);
                } else {
                    System.out.println("Not removed");
                }
            } finally {
                lock.unlock();
            }
        }

    }
}

public class Task10 {
    /**
     * Переписать упражнение 4, используя ReentrantLock
     */
    public static void main(String args[]) {
        Lock lock = new ReentrantLock();

        List<Integer> integerList = new ArrayList<>();

        MyThread10Plus threadPlus = new MyThread10Plus(integerList, lock);
        MyThread10Minus threadMinus = new MyThread10Minus(integerList, lock);

        threadPlus.start();
        threadMinus.start();

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
