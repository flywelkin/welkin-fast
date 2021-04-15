package io.gitee.welkinfast.admin.async;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/02/04 14:41
 * @Version 1.0.0
 */
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Thread（Runnable）        
        // Thread（RunnableFuture）      
        // Thread（FutureTask）
        //test1();
        test2();
    }

    private static void test1() throws InterruptedException, ExecutionException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("end");
                return 1024;
            }
        }); // 适配类
        new Thread(task, "A").start(); // 执行线程
        System.out.println(task.get());// 获取返回值， get()
    }

    private static void test2() throws InterruptedException, ExecutionException {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int i = new Random().nextInt(10);
                System.out.println(Thread.currentThread().getName() + " === end === "+ i);
                TimeUnit.SECONDS.sleep( new Random().nextInt(i));
                return i;
            }
        }); // 适配类
        new Thread(task, "A").start(); // 执行线程
        new Thread(task, "B").start(); // 执行线程
        System.out.println(task.get());// 获取返回值， get()
    }
}

