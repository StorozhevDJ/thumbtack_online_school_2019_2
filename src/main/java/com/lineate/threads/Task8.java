package com.lineate.threads;

import java.util.concurrent.Semaphore;

class Product {
    private int n;

    // Start with consumer semaphore unavailable.
    static Semaphore semCon = new Semaphore(0);
    static Semaphore semProd = new Semaphore(1);

    public void get() {
        try {
            semCon.acquire();
            System.out.println("Got: " + n);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        } finally {
            semProd.release();
        }
    }

    public void put(int n) {
        try {
            semProd.acquire();
            this.n = n;
            System.out.println("Put: " + n);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException caught");
        } finally {
            semCon.release();

        }
    }
}

class Producer extends Thread {
    private Product q;

    public Producer(Product q) {
        this.q = q;
    }

    public void run() {
        for (int i = 0; i < 20; i++)
            q.put(i);
    }
}

class Consumer extends Thread {
    private Product q;

    public Consumer(Product q) {
        this.q = q;
    }

    public void run() {
        for (int i = 0; i < 20; i++)
            q.get();
    }
}

public class Task8 {
    public static void main(String args[]) {
        Product q = new Product();
        new Consumer(q).start();
        new Producer(q).start();
    }
}
