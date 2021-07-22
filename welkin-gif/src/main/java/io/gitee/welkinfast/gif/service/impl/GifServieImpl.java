package io.gitee.welkinfast.gif.service.impl;

import cn.hutool.core.io.FileUtil;
import io.gitee.welkinfast.gif.entity.GifEntity;
import io.gitee.welkinfast.gif.handler.GifHandler;
import io.gitee.welkinfast.gif.service.GifServie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/16 09:39
 * @Version 1.0.0
 */
@Slf4j
@Service
public class GifServieImpl implements GifServie {

    private static final String SUFFIX_SEPARATOR = ".";

    @Override
    public GifEntity video2Gif(GifEntity entity) {

        if (entity.isClip()) {
            // 裁剪视频
            String newVideoPath = generateNewVideoPath(entity.getPath());
            entity.setOutPath(newVideoPath);
            GifHandler.videoClip(entity);
            FileUtil.del(new File(entity.getPath()));
            entity.setPath(newVideoPath);
        }

        // 生成gif图片
        String gifPath = generateGifPath(entity.getPath());
        entity.setOutPath(gifPath);
        GifHandler.video2Gif(entity);
        FileUtil.del(new File(entity.getPath()));

        return entity;
    }


    /**
     * 生成新的视频地址
     * test/video.mp4
     * test/video_new.mp4
     *
     * @param path
     * @return
     */
    private String generateNewVideoPath(String path) {
        int i = path.lastIndexOf(SUFFIX_SEPARATOR);
        String prefix = path.substring(0, i);
        String suffix = path.substring(i, path.length());
        return prefix + "_new" + suffix;
    }

    private String generateGifPath(String path) {
        int i = path.lastIndexOf(SUFFIX_SEPARATOR);
        String prefix = path.substring(0, i);
        return prefix + SUFFIX_SEPARATOR + "gif";
    }

    public static void main(String[] args) {
        String path = "test/test.mp4";
        System.out.println(path);
        GifServieImpl gifServie = new GifServieImpl();
        String newVideoPath = gifServie.generateNewVideoPath(path);
        System.out.println(newVideoPath);
        String gifPath = gifServie.generateGifPath(newVideoPath);
        System.out.println(gifPath);
    }
}
