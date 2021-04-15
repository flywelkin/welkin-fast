package io.gitee.welkinfast.admin.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  线程池
 * @Author yuanjg
 * @CreateTime 2021/02/04 17:32
 * @Version 1.0.0
 */
public class ThreadExecutorTest {

    public static void main(String[] args) {

        // 线程池  Executors原生三大方法
        ExecutorService threadpool1 = Executors.newFixedThreadPool(5); // 固定大小
        ExecutorService threadpool2 = Executors.newCachedThreadPool(); //可以弹性伸缩的线程池，遇强则强
        ExecutorService threadpool = Executors.newSingleThreadExecutor(); // 只有一个


        try {
            // 10个线程，会显示几个线程~
            for (int i = 1; i <= 100; i++) {
                // 线程池，执行线程
                threadpool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " running...");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 线程池关闭
            threadpool.shutdown();
        }


    }
}
