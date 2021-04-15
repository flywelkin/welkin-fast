package io.gitee.welkinfast.common.id;

import java.util.UUID;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/08/13 15:44
 * @Version 1.0.0
 */
public class CustomIdGenerator {

    public static String getId() {
        return UUID.randomUUID().toString();
    }

    public static String getNext() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
