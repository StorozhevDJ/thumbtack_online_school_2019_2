package com.lineate.threads;

class MyThread3 extends Thread {
    public void run() {
        try {
            System.out.println("Thread is running");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println("Thread exit.");
    }
}

public class Task3 {
    /**
     * Создать 3 новых потока и дождаться окончания их всех в первичном потоке.
     * Для каждого потока создать свой собственный экземпляр класса.
     */
    public static void main(String args[]) {
        MyThread3 thread1 = new MyThread3();
        MyThread3 thread2 = new MyThread3();
        MyThread3 thread3 = new MyThread3();

        thread1.start();
        thread2.start();
        thread3.start();

        System.out.println("Thread 1 is alive: " + thread1.isAlive());
        System.out.println("Thread 2 is alive: " + thread2.isAlive());
        System.out.println("Thread 3 is alive: " + thread3.isAlive());

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
        System.out.println("Thread is alive: " + thread1.isAlive());
        System.out.println("Thread is alive: " + thread2.isAlive());
        System.out.println("Thread is alive: " + thread3.isAlive());

        System.out.println("Main thread exiting.");
    }
}
