package com.lineate.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Task15 {

    /**
     * Реализовать очередь данных.
     * Данные - экземпляр класса  Data с единственным методом int[] get().
     * Потоки-писатели ставят в очередь экземпляры Data, количество экземпляров Data,
     * которое ставит в очередь каждый писатель, определяется в его конструкторе.
     * Потоки - читатели берут их из очереди и распечатывают, и в конечном итоге
     * должны взять все экземпляры Data, которые записали все писатели вместе взятые.
     * Количество тех и других потоков может быть любым и определяется в main.
     */


    public static void main(String args[]) {

        BlockingQueue<Data> queue = new LinkedBlockingQueue<>();

        Consumer2 consumer1 = new Consumer2(queue);
        Consumer2 consumer2 = new Consumer2(queue);
        Producer2 producer1 = new Producer2(queue, 150);
        Producer2 producer2 = new Producer2(queue, 250);

        consumer1.start();
        consumer2.start();
        producer1.start();
        producer2.start();

        try {
            producer1.join();
            producer2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }

        try {
            queue.put(new Data(null));  //Poison
            queue.put(new Data(null));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            consumer1.join();
            consumer2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
    }
}


class Data {

    private int[] array;

    public Data(int[] array) {
        this.array = array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public int[] getArray() {
        return array;
    }
}


class Producer2 extends Thread {
    private BlockingQueue<Data> queue;
    private int count;

    public Producer2(BlockingQueue<Data> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println("Producer started! Count: " + count);
        try {
            for (int i = 0; i < count; i++) {
                queue.put(new Data(new int[10]));
                System.out.println("Producer added Data " + i + ". Data in queue = " + queue.size());
            }
            System.out.println("Producer finished!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer2 extends Thread {
    private BlockingQueue<Data> queue;

    public Consumer2(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Consumer started!");
        try {
            for (; true; ) {
                Data data = queue.take();
                if (data != null) {
                    if (data.getArray() != null) {
                        System.out.println("Consumer getting Data " + data + ". Queue size = " + queue.size());
                    } else {
                        System.out.println("Consumer getting null data (poison) ");
                        break;
                    }
                }
            }
            System.out.println("Consumer finished!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

