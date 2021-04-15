package io.gitee.welkinfast.admin.queue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 *   阻塞队列
 * 每一个 put 操作。必须等待一个take。否则无法继续添加元素
 * @Author yuanjg
 * @CreateTime 2021/02/04 16:04
 * @Version 1.0.0
 */
public class ArrayBlockingQueueTest {

    public static void main(String[] args) throws Exception {
        //test1();
        //test2();
        //test3();
        test4();
    }

    //如果对列为空 则element() remove() 会抛异常
    private static void test1() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                int i1 = new Random().nextInt(100);
                // add返回布尔值        
                while (!queue.add(String.valueOf(i1))) {
                }
                System.out.println(Thread.currentThread().getName() + " == " + i1);
            }, String.valueOf(i)).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(queue.size());

        System.out.println(queue.element());
        System.out.println(queue.remove());

        System.out.println(queue.element());
        System.out.println(queue.remove());

        System.out.println(queue.element());
        System.out.println(queue.remove());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(queue.element());
        System.out.println(queue.remove());
    }

    //不抛异常
    private static void test2() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        // add返回布尔值        
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                int i1 = new Random().nextInt(100);
                //在不超过队列容量的情况下立即将指定的元素插入此队列的尾部，则在成功插入时返回true ，
                // 如果此队列已满，则返回false 。 通常，此方法比add方法更可取，后者只能通过引发异常而无法插入元素
                while (!queue.offer(String.valueOf(i1))) {
                }
                System.out.println(Thread.currentThread().getName() + " == " + i1);
            }, String.valueOf(i)).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(queue.size());

        System.out.println(queue.peek());
        System.out.println(queue.poll());

        System.out.println(queue.peek());
        System.out.println(queue.poll());

        System.out.println(queue.peek());
        System.out.println(queue.poll());


        System.out.println(queue.peek());
        System.out.println(queue.poll());
    }


    //一直阻塞
    public static void test3() throws InterruptedException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                int i1 = new Random().nextInt(100);
                try {
                    // 将指定的元素插入此队列的末尾，如果队列已满，则等待空间变为可用
                    queue.put(String.valueOf(i1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " == " + i1);
            }, String.valueOf(i)).start();
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(queue.size());

        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());
        System.out.println(queue.take());

    }


    public static void test4() throws InterruptedException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(3);
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        //将指定的元素插入此队列的尾部，如果队列已满，则等待指定的等待时间以使空间变得可用
        System.out.println(queue.offer("d", 3L, TimeUnit.SECONDS));

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll(3L, TimeUnit.SECONDS)); // 阻塞
    }

    public static void test5() {
        // 不用写参数
        SynchronousQueue<String> queue = new SynchronousQueue<>();

    }
}
