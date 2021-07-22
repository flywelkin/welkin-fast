package io.gitee.welkinfast.gif.handler;

import cn.hutool.core.io.FileUtil;
import io.gitee.welkinfast.common.error.CustomErrorType;
import io.gitee.welkinfast.common.error.CustomExcption;
import io.gitee.welkinfast.gif.entity.GifEntity;
import io.gitee.welkinfast.gif.utils.CommandUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/16 09:13
 * @Version 1.0.0
 */
@Slf4j
public class GifHandler {

    /**
     * 视频剪辑
     * ffmpeg -i test.mp4 -vf crop=w=100:h=100:x=12:y=34 out.mp4
     *
     * @param video
     */
    public static void videoClip(GifEntity video) {
        checkFileIsExist(video.getPath());
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(video.getPath());
        command.add("-vf");
        command.add(String.format("crop=w=%d:h=%d:x=%d:y=%d", video.getOWidth(), video.getOHeight(), video.getX(), video.getY()));
        if (StringUtils.equalsIgnoreCase(GifEntity.SPEED, video.getQuality())) {
            // 急速
        }
        if (StringUtils.equalsIgnoreCase(GifEntity.STANDARD, video.getQuality())) {
            // 标准
        }
        if (StringUtils.equalsIgnoreCase(GifEntity.HD, video.getQuality())) {
            // 高清
        }
        command.add(video.getOutPath());

        log.info("视频裁剪开始, {}", StringUtils.join(command, " "));
        CommandUtil.exec(command);
        log.info("视频裁剪完成, {} ==> {}", video.getPath(), video.getOutPath());
    }

    /**
     * 视频转gif
     * ffmpeg -i test.mp4 -b:v 2048k test.gif
     *
     * @param gif
     */
    public static void video2Gif(GifEntity gif) {
        checkFileIsExist(gif.getPath());
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(gif.getPath());
        if (StringUtils.equalsIgnoreCase(GifEntity.SPEED, gif.getQuality())) {
            // 急速
        }
        if (StringUtils.equalsIgnoreCase(GifEntity.STANDARD, gif.getQuality())) {
            // 标准
        }
        if (StringUtils.equalsIgnoreCase(GifEntity.HD, gif.getQuality())) {
           // 高清
        }
        command.add(gif.getOutPath());

        log.info("GIF转换开始, {}", StringUtils.join(command, " "));
        CommandUtil.exec(command);
        log.info("GIF转换完成, {} ==> {}", gif.getPath(), gif.getOutPath());
    }

    private static void checkFileIsExist(String path) {
        if (!FileUtil.exist(path)) {
            throw new CustomExcption(CustomErrorType.FILE_NOT_EXIST, String.format("文件%不存在", path));
        }
    }
}
