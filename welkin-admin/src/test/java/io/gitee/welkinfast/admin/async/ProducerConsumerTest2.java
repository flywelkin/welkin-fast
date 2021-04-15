package io.gitee.welkinfast.admin.async;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/02/04 10:06
 * @Version 1.0.0
 */
public class ProducerConsumerTest2 {

    public static void main(String[] args) {
        Data2 data = new Data2();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();


        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();


        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}

class Data2 {

    private int source = 0;

    private final Lock lock = new ReentrantLock();
    //获得生产者等待队列对象
    private final Condition productorCondition = lock.newCondition();
    //获得消费者等待队列对象
    private final Condition customerCondition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
        try {
            while (source != 0) {
                productorCondition.await();
            }
            source++;
            System.out.println("生产资源 - 当前资源数: " + source + " - " + Thread.currentThread().getName());
            customerCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            while (source == 0) {
                customerCondition.await();
            }
            source--;
            System.out.println("消费资源 - 当前资源数: " + source + " - " + Thread.currentThread().getName());
            productorCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}