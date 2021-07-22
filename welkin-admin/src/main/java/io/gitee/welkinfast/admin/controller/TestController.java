package io.gitee.welkinfast.admin.controller;

import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/30 09:52
 * @Version 1.0.0
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public void test(HttpServletRequest request) {
        AsyncContext asyncContext = request.startAsync();   // 创建一个异步上下文对象
        asyncContext.addListener(new AsyncListener() {   //设置监听器:可设置其开始、完成、异常、超时等事件的回调处理
            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                System.out.println("超时了...");
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                System.out.println("线程开始");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                System.out.println("发生错误：" + event.getThrowable());
            }

            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                System.out.println("执行完成");
            }
        });
        asyncContext.setTimeout(20000);   //设置超时时间
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
//                try {
//                    Thread.sleep(10000);
//                    System.out.println("内部线程：" + Thread.currentThread().getName());
//                    asyncContext.getResponse().setCharacterEncoding("utf-8");
//                    asyncContext.getResponse().setContentType("text/html;charset=UTF-8");
//                    asyncContext.getResponse().getWriter().println("这是异步的请求返回");   //异步请求完成通知
//
//
//                } catch (Exception e) {
//                    System.out.println("异常：" + e);
//                }
//                asyncContext.complete();  //此时整个请求才完成
                throw new CustomExcption(CustomErrorType.TASK_ERROR);
            }
        });
        //此时之类 request的线程连接已经释放了
        System.out.println("主线程：" + Thread.currentThread().getName());


    }
}
