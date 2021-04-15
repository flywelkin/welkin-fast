package io.gitee.welkinfast.admin.async;

import java.util.concurrent.CyclicBarrier;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/02/04 14:58
 * @Version 1.0.0
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        // 等待cyclicBarrier计数器满，就执行后面的Runnable，不满就阻塞        
        CyclicBarrier cyclicBarrier = new CyclicBarrier(8, new Runnable() {
            // 调用八次cyclicBarrier.await()后执行
            @Override
            public void run() {
                System.out.println("神龙召唤成功！");
            }
        });
        for (int i = 0; i <= 7; i++) {
            final int temp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "收集了 第" + temp + "颗龙珠");
                try {
                    cyclicBarrier.await(); // 等待 阻塞              
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
