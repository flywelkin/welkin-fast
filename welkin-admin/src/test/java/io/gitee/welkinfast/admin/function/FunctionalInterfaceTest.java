package io.gitee.welkinfast.admin.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 *  函数式编程
 * @Author yuanjg
 * @CreateTime 2021/02/05 16:28
 * @Version 1.0.0
 */
public class FunctionalInterfaceTest {

    public static void main(String[] args) {

        //函数式编程==>传入一个参数返回一个结果
        Function<String,Integer> function = String::length;
        System.out.println(function.apply("abc"));

        //断定型接口==>有且只有一个参数，返回一个布尔值
        Predicate<String> predicate = String::isEmpty;
        System.out.println(predicate.test("abced"));

        //消费型接口==>没有返回值，只能传递参数  消费者
        Consumer<String> consumer = System.out::println;
        consumer.accept("123");

        //供给型接口==>只有返回值，没有参数  生产者
        Supplier<String> supplier = ()->{return "aaa";};
        System.out.println(supplier.get());
    }
}
