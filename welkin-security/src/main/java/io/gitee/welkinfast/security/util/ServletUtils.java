package io.gitee.welkinfast.security.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/15 13:46
 * @Version 1.0.0
 */
public class ServletUtils {
    /**
     * 渲染到客户端
     *
     * @param object 待渲染的实体类，会自动转为json
     */
    public static void render(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.getWriter().print(JSONObject.toJSONString(object));
    }
}