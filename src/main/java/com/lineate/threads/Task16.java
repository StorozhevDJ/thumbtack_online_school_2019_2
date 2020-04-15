package com.lineate.threads;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public class Task16 {
    /**
     * Реализовать очередь задач.
     * Задача - экземпляр класса  Task или его наследника,
     * имплементирующего Executable - свой интерфейс с единственным методом void execute().
     * Потоки - разработчики ставят в очередь экземпляры Task аналогично (15),
     * потоки - исполнители берут их из очереди и исполняют.
     * Количество тех и других потоков может быть любым и определяется в main.
     */

    public static void main(String[] args) {
        BlockingQueue<MyTask16> queue = new LinkedBlockingQueue<>();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            executorService.submit(new Developer(queue, 10));
            executorService.submit(new Executor(queue));
        }
        executorService.shutdown();
    }
}


interface Executable {
    void execute() throws InterruptedException;
}


class MyTask16 implements Executable {
    @Override
    public void execute() throws InterruptedException {
        System.out.println("Task start");
        Thread.sleep(100);
        System.out.println("Task end");
    }
}


class Developer implements Runnable {

    private BlockingQueue<MyTask16> queue;
    private int count;

    public Developer(BlockingQueue<MyTask16> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println("Developer started!");
        try {
            for (int i = 0; i < count; i++) {
                queue.put(new MyTask16());
                System.out.println("Developer added Task " + i * count + ". Tasks in queue = " + queue.size());
            }
            queue.put(null);    //Add poison
            System.out.println("Developer end!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


class Executor implements Runnable {

    private BlockingQueue<MyTask16> queue;

    public Executor(BlockingQueue<MyTask16> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Executor started!");
        while (true) {
            try {
                MyTask16 myTask16 = queue.take();
                if (myTask16 != null) {
                    System.out.println("Executor task: " + myTask16);
                    myTask16.execute();
                    Thread.sleep(100);
                } else {
                    break; //If poison
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Executor end! ");
    }
}
