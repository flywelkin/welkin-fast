package io.gitee.welkinfast.gif.utils;

import io.gitee.welkinfast.gif.utils.os.OSinfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/16 08:55
 * @Version 1.0.0
 */
@Slf4j
public class CommandUtil {

    public static void exec(List<String> command) {
        try {
            if (OSinfo.isWindows()) {
                ProcessBuilder builder = new ProcessBuilder();
                builder.command(command);
                Process process = builder.start();
                if (process.isAlive()) {
                    dealStream(process);
                    process.waitFor();
                }
            } else {
                Runtime runtime = Runtime.getRuntime();
                Process process = runtime.exec(StringUtils.join(command, " "));
                if (process.isAlive()) {
                    dealStream(process);
                    process.waitFor();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理process输出流和错误流，防止进程阻塞
     * 在process.waitFor();前调用
     *
     * @param process
     */
    private static void dealStream(Process process) {
        if (process == null) {
            return;
        }

        // 处理InputStream的线程
        new Thread() {
            @Override
            public void run() {
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                try {
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        // 处理ErrorStream的线程
        new Thread() {
            @Override
            public void run() {
                BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line = null;
                try {
                    while ((line = err.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        err.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
