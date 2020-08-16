package io.gitee.welkinfast.common.id;

import java.util.UUID;

/**
 * @Description TODO
 * @Author yuanjg
 * @CreateTime 2020/08/13 15:44
 * @Version 1.0.0
 */
public class WelkinIdGenerator {

    public static String getId(){
        return UUID.randomUUID().toString();
    }
}
