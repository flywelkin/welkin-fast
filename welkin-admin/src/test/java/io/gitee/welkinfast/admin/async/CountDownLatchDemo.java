package io.gitee.welkinfast.admin.async;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch countDownLatch = new CountDownLatch(6);
 * countDownLatch.countDown(); 出去一个人计数器就 -1
 * countDownLatch.await();  阻塞等待计数器归零
 *
 * @Author yuanjg
 * @CreateTime 2021/02/04 14:52
 * @Version 1.0.0
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    /**
     * 使用CountDownLatch同步减法计数器，等待异步线程执行完成
     *
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6); // 初始值
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " --- start");
                // 出去一个人计数器就 -1
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        // 阻塞等待计数器归零
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " === end");
    }
}
