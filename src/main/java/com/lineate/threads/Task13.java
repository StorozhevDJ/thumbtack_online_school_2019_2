package com.lineate.threads;

import java.text.SimpleDateFormat;
import java.util.Date;


class Formatter {

    private Date date;
    private ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<>();

    public Formatter(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String format(Date date) {
        SimpleDateFormat simpleDateFormat = getThreadLocalSimpleDateFormat();
        return simpleDateFormat.format(date);
    }


    private SimpleDateFormat getThreadLocalSimpleDateFormat() {
        SimpleDateFormat sdf = simpleDateFormatThreadLocal.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat();
            simpleDateFormatThreadLocal.set(sdf);
        }
        return sdf;
    }
}


class MyTask extends Thread {

    private Formatter formatter;

    public MyTask(Formatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void run() {
        String str = formatter.format(formatter.getDate());
        System.out.println(formatter.getDate() + " -> " + str);
    }
}


public class Task13 {

    /**
     * Написать класс Formatter, с методом format(Date date), форматирующим дату-время.
     * Для форматирования возьмите класс SimpleDateFormat.
     * В основном потоке создать единственный экземпляр класса Formatter и 5 потоков,
     * каждому потоку передается при инициализации этот экземпляр.
     * Потоки должны корректно форматировать дату-время, синхронизация не разрешается.
     * Использовать ThreadLocal.
     */

    public static void main(String[] args) {
        Formatter formatter = new Formatter(new Date());

        MyTask t1 = new MyTask(formatter);
        MyTask t2 = new MyTask(formatter);
        MyTask t3 = new MyTask(formatter);
        MyTask t4 = new MyTask(formatter);
        MyTask t5 = new MyTask(formatter);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}