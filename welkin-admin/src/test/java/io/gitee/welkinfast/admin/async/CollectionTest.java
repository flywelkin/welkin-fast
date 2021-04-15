package io.gitee.welkinfast.admin.async;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 多线程操作集合类ArrayList不安全
 * 1、 故障现象： ConcurrentModificationException
 * 2、 解决方案：
 * List<String> list = new Vector<>(); // Vector 是一个线程安全的类,效率低下
 * List<String> list = Collections.synchronizedList(new ArrayList<>());
 * List<String> list = new CopyOnWriteArrayList<>();  // JUC推荐使用
 *
 *
 * 并发下 HashSet 存在安全的问题
 * 1、 故障现象： ConcurrentModificationException 并发修改异常
 * 2、 解决方案：
 * Set<String> set = Collections.synchronizedSet(new HashSet<>());
 * Set<String> set =new CopyOnWriteArraySet<>()
 *
 * 并发下 HashMap 不安全
 * 1、 故障现象： ConcurrentModificationException 并发修改异常
 * 2、 解决方案：
 * Map<String, String> map = new ConcurrentHashMap<>();
 *
 * @Author yuanjg
 * @CreateTime 2021/02/04 09:09
 * @Version 1.0.0
 */
@Slf4j
public class CollectionTest {


    public static void main(String[] args) {
        //arrayListTest();
        //vectorTest();
        //synchronizedListTest();
        copyOnWriteArrayListTest();
    }

    private static void arrayListTest() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(Thread.currentThread().getName() + "--" + list);
            }, String.valueOf(i)).start();
        }
    }

    private static void vectorTest() {
        List<String> list = new Vector<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(Thread.currentThread().getName() + "--" + list);
            }, String.valueOf(i)).start();
        }
    }

    private static void synchronizedListTest() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(Thread.currentThread().getName() + "--" + list);
            }, String.valueOf(i)).start();
        }
    }

    private static void copyOnWriteArrayListTest() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(Thread.currentThread().getName() + "--" + list);
            }, String.valueOf(i)).start();
        }
    }
}
