package io.gitee.welkinfast.im.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * 
 * @Author yuanjg
 * @CreateTime 2021/03/21 11:47
 * @Version 1.0.0
 */
@Slf4j
public class IOUtil {

    public static String getHostAddress() {
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ex) {
            log.error("获取ip报错", ex.getMessage());
        }
        return ip;
    }

}

