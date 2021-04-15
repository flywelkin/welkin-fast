package io.gitee.welkinfast.admin.async;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/02/04 09:36
 * @Version 1.0.0
 */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 卖票   自己会写     3个售票员卖出30张票 * 企业中禁止这样写，Coding：企业级开发！ * <p> * 多线程编程的固定套路： * 1、高内聚，低耦合  （前提） * 2、线程  操作(调用对外暴露的方法)   资源类  (要点)
 */
public class SaleTicketTest2 {
    public static void main(String[] args) {
        SaleTicket2 saleTicket = new SaleTicket2();
        new Thread(() -> {
            for (int i = 1; i < 40; i++) saleTicket.saleTicket();
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i < 40; i++) saleTicket.saleTicket();
        }, "B").start();
        new Thread(() -> {
            for (int i = 1; i < 40; i++) saleTicket.saleTicket();
        }, "C").start();
    }
}

// 属性，和方法  高内聚
class SaleTicket2 {
    private int number = 30;
    // 锁LOCK
    private Lock lock = new ReentrantLock(); // 可重入
    // 卖票方法    
    public void saleTicket() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出第" + (number--) + "还剩下：" + number + "张票");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

