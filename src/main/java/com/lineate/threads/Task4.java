package com.lineate.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyThread4Plus extends Thread {
    private final List<Integer> integerList;

    public MyThread4Plus(List<Integer> integer) {
        this.integerList = integer;
    }

    public void run() {
        int rand;
        for (int i = 0; i < 10000; i++) {
            synchronized (integerList) {
                rand = (int) (Math.random() * 1000);
                integerList.add(rand);
                System.out.println("Added " + rand);
            }
        }
    }
}

class MyThread4Minus extends Thread {
    private final List<Integer> integerList;

    public MyThread4Minus(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public void run() {
        int val;
        int ptr;
        for (int i = 0; i < 10000; i++) {
            synchronized (integerList) {
                if (integerList.size() != 0) {
                    ptr = (int) (Math.random() * (integerList.size() - 1));
                    val = integerList.get(ptr);
                    integerList.remove(ptr);
                    System.out.println("Removed value " + val + " by ptr " + ptr);
                } else {
                    System.out.println("Not removed");
                }
            }
        }
    }
}

public class Task4 {
    /**
     * В основном потоке создать ArrayList<Integer>.
     * Запустить 2 потока на базе разных классов, один поток 10000 раз добавляет в список случайное целое число,
     * другой 10000 раз удаляет элемент по случайному индексу (если при удалении выясняется, что список пуст,
     * ничего не делать).
     * Использовать внешний synchronized блок.
     * Потоки должны работать конкурентно, то есть одновременно должно идти и добавление, и удаление.
     */
    static private ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String args[]) {
        List<Integer> integerList = new ArrayList<>();

        MyThread4Plus threadPlus = new MyThread4Plus(integerList);
        MyThread4Minus threadMinus = new MyThread4Minus(integerList);

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
