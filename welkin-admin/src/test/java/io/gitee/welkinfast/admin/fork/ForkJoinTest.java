package io.gitee.welkinfast.admin.fork;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/02/05 16:44
 * @Version 1.0.0
 */
public class ForkJoinTest {
    public static void main(String[] args) {
         test1();  // 10582 ms    60
         test2();  // 9965 ms    90
         test3();  // 158 ms   101
    }

    // 正常测试
    public static void test1(){
        long start =  System.currentTimeMillis();
        long sum = 0L;
        for (long i = 0L; i <= 1000000000 ; i++) {
            sum +=i;
        }
        long end =  System.currentTimeMillis();
        System.out.println("time:"+(end-start)+" sum:"+sum);
    }


    // ForkJoin测试
    public static void test2(){
        long start =  System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinDemo forkJoinDemo = new ForkJoinDemo(0L,10_0000__0000L);
        Long sum = forkJoinPool.invoke(forkJoinDemo);
        long end =  System.currentTimeMillis();
        System.out.println("time:"+(end-start)+" sum:"+sum);
    }

    // Stream并行流测试
    public static void test3(){
        long start =  System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0, 1000000000).parallel().reduce(0, Long::sum);
        long end =  System.currentTimeMillis();
        System.out.println("time:"+(end-start)+" sum:"+sum);
    }
}

class ForkJoinDemo extends RecursiveTask<Long> {
    private final Long start;//起始值
    private final Long end;//结束值
    public static final Long critical = 100000L;//临界值

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        // return null;
        //判断是否是拆分完毕
        long lenth = end - start;   //起始值差值
        if (lenth <= critical) {
            //如果拆分完毕就相加
            long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            //没有拆分完毕就开始拆分
            long middle = (end + start) / 2;//计算的两个值的中间值
            ForkJoinDemo right = new ForkJoinDemo(start, middle);
            right.fork();//拆分，并压入线程队列
            ForkJoinDemo left = new ForkJoinDemo(middle + 1, end);
            left.fork();//拆分，并压入线程队列
            //合并
            return right.join() + left.join();
        }

    }
}