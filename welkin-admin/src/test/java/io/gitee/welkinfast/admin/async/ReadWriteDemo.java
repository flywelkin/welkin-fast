package io.gitee.welkinfast.admin.async;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/02/04 17:28
 * @Version 1.0.0
 */
public class ReadWriteDemo {

    public static void main(String[] args) {

        MyCache2 myCache = new MyCache2();

        // 多个线程同时进行读写
        // 五个线程在写  线程是CPU调度的
        for (int i = 1; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(temp + "", temp + "");
            }, String.valueOf(i)).start();
        }

        // 五个线程在读
        for (int i = 1; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.get(temp + "");
            }, String.valueOf(i)).start();
        }


    }
}

// 线程操作资源类，存在问题的
class MyCache {

    private volatile Map<String, Object> map = new HashMap<>();

    // 没有加读写锁的时候，第一个线程还没有写入完成，可能会存在其他写入~

    // 写。独占
    public void put(String key, String value) {
        System.out.println(Thread.currentThread().getName() + "写入" + key);
        map.put(key, value);
        // 存在别的线程插队
        System.out.println(Thread.currentThread().getName() + "写入完成");
    }

    // 读
    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + "读取" + key);
        Object result = map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取结果：" + result);
    }

}


// 线程操作资源类，存在问题的
class MyCache2 {

    private volatile Map<String, Object> map = new HashMap<>();

    // ReadWriteLock --> ReentrantReadWriteLock   lock不能区分读和写
    // ReentrantReadWriteLock 可以区分读和写，实现更加精确的控制
    // 读写锁
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 写。独占
    public void put(String key, String value) {
        // lock.lock 加锁
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key, value);
            // 存在别的线程插队
            System.out.println(Thread.currentThread().getName() + "写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock(); // lck.unlock();
        }
    }

    // 多线程下尽量加锁！

    // 读
    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读取" + key);
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取结果：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

}
