package com.lineate.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MyThread6Add extends Thread {
    List<Integer> integer;

    public MyThread6Add(List<Integer> integer) {
        this.integer = integer;
    }

    synchronized public void run() {
        int rand;
        for (int i = 0; i < 10000; i++) {
            rand = (int) (Math.random() * 1000);
            integer.add(rand);
            System.out.println("Added " + rand);
        }
    }
}

class MyThread6Remove extends Thread {
    List<Integer> integerList;

    public MyThread6Remove(List<Integer> integerList) {
        this.integerList = integerList;
    }

    synchronized public void run() {
        int val;
        int ptr;
        for (int i = 0; i < 10000; i++) {
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

public class Task6 {

    /**
     * Можно ли корректно решить задачу 4 , используя Collections. synchronizedList и не используя synchronized явно?
     * Если да - напишите программу.
     * <p>
     * Collections.synchronizedList в сравнении с ArrayList имеет синхронизированные операции add, remove и т.п.
     * Но итератор в данном случае не синхронизирован.
     * Поэтому, при использовании итератора, synchronizedList нужно обязательно синхронизировать вручную.
     *
     * @param args
     */
    public static void main(String args[]) {
        List<Integer> integerList = Collections.synchronizedList(new ArrayList());

        MyThread6Add threadAdd = new MyThread6Add(integerList);
        MyThread6Remove threadRemove = new MyThread6Remove(integerList);

        threadAdd.start();
        threadRemove.start();

        System.out.println("Thread + is alive: " + threadAdd.isAlive());
        System.out.println("Thread - is alive: " + threadRemove.isAlive());

        try {
            threadAdd.join();
            threadRemove.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
        System.out.println("Thread + is alive: " + threadAdd.isAlive());
        System.out.println("Thread - is alive: " + threadRemove.isAlive());

        System.out.println("Main thread exiting.");
    }
}
