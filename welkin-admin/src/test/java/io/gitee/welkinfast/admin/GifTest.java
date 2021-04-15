package io.gitee.welkinfast.admin;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.io.File;
import java.io.FileOutputStream;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/03/19 19:58
 * @Version 1.0.0
 */
public class GifTest {

    public static void main(String[] args) {
        // 将指定目录的 MP4 文件生成同名的 gif 文件
        File videoDir = new File("C:\\Users\\yuanjg\\Desktop\\gif");
        for (File file : videoDir.listFiles()) {
            String videoPath = file.getPath();
            if (videoPath.endsWith(".mp4")) {
                String gifPath = videoPath.substring(0, videoPath.lastIndexOf(".")) + ".gif";
                try {
                    video2Gif(videoPath, gifPath);
                } catch (Exception e) {
                    System.out.println("文件 [" + videoPath + "] 处理异常！");
                    e.printStackTrace();
                }
            }
        }
    }

    private static void video2Gif(String videoPath, String gifPath) throws Exception {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath);
             FileOutputStream targetFile = new FileOutputStream(gifPath)) {
            grabber.start();
            int frames = grabber.getLengthInFrames();
            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            encoder.setFrameRate(frames);
            encoder.start(targetFile);
            Java2DFrameConverter converter = new Java2DFrameConverter();
            for (int i = 0 ; i < frames ; i += 2) {// 8帧合成1帧？（反正越大动图越小、越快）
                encoder.setDelay((int) grabber.getDelayedTime());
                encoder.addFrame(converter.convert(grabber.grabImage()));
                grabber.setFrameNumber(i);
            }
            encoder.finish();
        }
    }


}
