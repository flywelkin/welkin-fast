package io.gitee.welkinfast.admin.queue;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 *  同步对列
 * @Author yuanjg
 * @CreateTime 2021/02/04 16:50
 * @Version 1.0.0
 */
public class SynchronousQueueTest {

    public static void main(String[] args) {

        // 不用写参数！
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "put 1");
                queue.put("1");
                System.out.println(Thread.currentThread().getName() + "put 2");
                queue.put("2");
                System.out.println(Thread.currentThread().getName() + "put 3");
                queue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();


        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + queue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + queue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "B").start();


    }
}
