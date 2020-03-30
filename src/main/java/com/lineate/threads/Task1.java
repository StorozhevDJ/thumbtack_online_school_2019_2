package com.lineate.threads;

public class Task1 {
    /**
     * Напечатать все свойства текущего потока.
     *
     * @param args
     */
    public static void main(String args[]) {
        Thread t = Thread.currentThread();

        System.out.println("Current thread Name: " + t.getName());
        System.out.println("Current thread ID: " + t.getId());
        System.out.println("Current thread priority: " + t.getPriority());
        System.out.println("Current thread state: " + t.getState());
        System.out.println("Current thread isAlive: " + t.isAlive());
    }
}
