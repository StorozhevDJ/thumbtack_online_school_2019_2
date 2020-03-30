package com.lineate.threads;

import java.util.concurrent.Semaphore;

public class Task7 {
    /**
     * “Ping Pong”, задача заключается в том чтобы бесконечно выводить на консоль сообщения “ping” или “pong”
     * из двух разных потоков. При этом сообщения обязаны чередоваться, т.е. не может быть ситуации
     * когда ping или pong появляется в консоли более одного раза подряд. Первым должно быть сообщение “ping”.
     */

    // Start with Ping semaphore unavailable.
    static Semaphore semPing = new Semaphore(1);
    static Semaphore semPong = new Semaphore(0);

    static Runnable runnablePingImpl = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    semPing.acquire();
                    System.out.println("Ping");
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException caught");
                } finally {
                    semPong.release();
                }
            }
        }
    };

    static Runnable runnablePongImpl = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    semPong.acquire();
                    System.out.println("Pong");
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException caught");
                } finally {
                    semPing.release();
                }
            }
        }
    };

    public static void main(String args[]) {
        System.out.println("Start main");
        new Thread(runnablePingImpl).start();
        new Thread(runnablePongImpl).start();
    }


}
