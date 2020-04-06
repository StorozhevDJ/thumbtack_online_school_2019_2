package com.lineate.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class PingPongSwitch {
    private Lock lock = new ReentrantLock();
    private Condition pongCondition = lock.newCondition();
    private Condition pingCondition = lock.newCondition();

    private int cnt;
    private boolean isPing = false;

    public void toPong(int x) throws InterruptedException {
        lock.lock();
        try {
            if (isPing) pongCondition.await();
            isPing = true;
            cnt = x;
            System.out.println("ping " + cnt);
            pingCondition.signal();
        } finally {
            lock.unlock();
        }
    }


    public void toPing() throws InterruptedException {
        lock.lock();
        try {
            if (!isPing) pingCondition.await();
            System.out.println("pong " + cnt);
            isPing = false;
            pongCondition.signal();
        } finally {
            lock.unlock();
        }
    }
}

class Ping extends Thread {
    private PingPongSwitch pingPongSwitch;
    private int pingPongCnt;

    public Ping(PingPongSwitch pingPongSwitch, int pingPongCnt) {
        this.pingPongSwitch = pingPongSwitch;
        this.pingPongCnt = pingPongCnt;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < pingPongCnt; i++) pingPongSwitch.toPong(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Pong extends Thread {
    private PingPongSwitch pingPongSwitch;
    private int pingPongCnt;

    public Pong(PingPongSwitch pingPongSwitch, int pingPongCnt) {
        this.pingPongSwitch = pingPongSwitch;
        this.pingPongCnt = pingPongCnt;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < pingPongCnt; i++) pingPongSwitch.toPing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


public class Task11 {
    /**
     * “Ping Pong” на базе ReentrantLock и Conditional переменной
     */
    public static void main(String[] args) {

        PingPongSwitch pingPongSwitch = new PingPongSwitch();

        Ping ping = new Ping(pingPongSwitch, 200);
        Pong pong = new Pong(pingPongSwitch, 200);

        ping.start();
        pong.start();

        try {
            ping.join();
            pong.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
