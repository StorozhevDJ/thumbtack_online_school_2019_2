package com.lineate.threads;

class MyThread extends Thread {
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

public class Task2 {

    /**
     * Создать новый поток и дождаться его окончания в первичном потоке.
     */
    public static void main(String args[]) {
        MyThread thread = new MyThread();
        thread.start();

        System.out.println("Thread is alive: " + thread.isAlive());

        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
        System.out.println("Thread is alive: " + thread.isAlive());

        System.out.println("Main thread exiting.");
    }
}
