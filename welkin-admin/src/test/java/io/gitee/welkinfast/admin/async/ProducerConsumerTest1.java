package io.gitee.welkinfast.admin.async;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/02/04 09:44
 * @Version 1.0.0
 */
public class ProducerConsumerTest1 {

    public static void main(String[] args) {
        Data1 data = new Data1();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();



        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }

}

class Data1 {
    private int source = 0;

    public synchronized void increment() throws InterruptedException {
        //判断        
        while (source != 0) {
            this.wait();
        }
        source++;
        System.out.println("生产资源 - 当前资源数: " + source + " - " + Thread.currentThread().getName());
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        while (source == 0) {
            this.wait();
        }
        source--;
        System.out.println("消费资源 - 当前资源数: " + source + " - " + Thread.currentThread().getName());
        this.notifyAll();
    }
}