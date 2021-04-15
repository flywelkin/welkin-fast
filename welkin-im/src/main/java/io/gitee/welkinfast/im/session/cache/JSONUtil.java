package io.gitee.welkinfast.im.session.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * GSON 工具类
 *
 * @Author yuanjg
 * @CreateTime 2021/4/7 14:35
 * @Version 1.0.0
 */
public class JSONUtil {

    //谷歌 GsonBuilder 构造器
    static GsonBuilder gb = new GsonBuilder();
    private static final Gson gson;

    static {
        gb.disableHtmlEscaping();
        gson = gb.create();
    }


    //使用谷歌 Gson 将 POJO 转成字符串
    public static String toJSONString(Object obj) {
        String json = gson.toJson(obj);
        return json;
    }


    //使用谷歌 Gson  将字符串转成 POJO对象
    public static <T> T parseObject(String json, Class<T> tClass) {
        T t = gson.fromJson(json, tClass);
        return t;
    }


    //使用 谷歌 json 将字符串转成 POJO对象
    public static <T> T parseObject(String json, Type type) {
        T t = gson.fromJson(json, type);
        return t;
    }

    //使用阿里 Fastjson 将字符串转成 POJO对象
    public static <T> T jsonToPojo(String json, TypeReference<T> type) {
        T t = JSON.parseObject(json, type);
        return t;
    }

}
