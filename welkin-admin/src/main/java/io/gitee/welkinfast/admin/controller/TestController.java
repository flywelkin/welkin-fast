package io.gitee.welkinfast.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/09/08 20:44
 * @Version 1.0.0
 */
@Controller
public class TestController {

    @GetMapping("/test")
    public void test(HttpServletResponse response) throws IOException, InterruptedException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        PrintWriter writer = response.getWriter();
        int i = 0;
        while (i<=100){
            i++;
            Thread.sleep(100);
            writer.print("test");
            writer.flush();
            response.flushBuffer();
        }
    }
}
