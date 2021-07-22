package io.gitee.welkinfast.gif;

import io.gitee.welkinfast.gif.entity.GifEntity;
import io.gitee.welkinfast.gif.service.impl.GifServieImpl;
import io.gitee.welkinfast.gif.utils.CommandUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/16 09:09
 * @Version 1.0.0
 */
public class GitTest {

    public static void main(String[] args) {
        //test();
        video2GifTest();
    }

    private static void test() {
        List<String> list = new ArrayList<>();
        list.add("ffmpeg");
        list.add("-i");
        list.add("C:/Users/yuanjg/Desktop/test.mp4");
        list.add("-b:v");
        list.add("2048k");
        list.add("C:/Users/yuanjg/Desktop/test222.gif");
        CommandUtil.exec(list);
    }

    public static void video2GifTest() {
        String path = "C:/Users/yuanjg/Desktop/test.mp4";
        GifEntity video = new GifEntity();
        video.setPath(path);

        video.setIsClip(1);
        video.setOWidth(544);
        video.setOHeight(960);
        video.setOWidth(544);
        video.setOHeight(544);
        video.setX(0);
        video.setY(120);
        GifServieImpl gifServie = new GifServieImpl();
        gifServie.video2Gif(video);
    }
}
