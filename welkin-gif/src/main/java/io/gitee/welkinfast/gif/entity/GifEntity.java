package io.gitee.welkinfast.gif.entity;

import lombok.Data;

/**
 * @Author yuanjg
 * @CreateTime 2021/04/16 09:15
 * @Version 1.0.0
 */
@Data
public class GifEntity {

    /**
     * 极速
     */
    public static final String SPEED = "speed";
    /**
     * 标准
     */
    public static final String STANDARD = "standard";
    /**
     * 高清
     */
    public static final String HD = "hd";

    /**
     * 资源路径
     */
    private String path;

    /**
     * 资源宽度
     */
    private Integer width;

    /**
     * 资源高度
     */
    private Integer height;

    /**
     * 视频时长(毫秒)
     */
    private Double duration;

    /**
     * 视频大小(字节)
     */
    private Double size;

    /**
     * 转换品质 极速 speed 标准 standard 高清 hd
     */
    private String quality;


    /**
     * 是否裁剪
     */
    private Integer isClip;

    /**
     * 输出宽度
     */
    private Integer oWidth;

    /**
     * 输高宽度
     */
    private Integer oHeight;

    /**
     * X轴偏移
     */
    private Integer x;

    /**
     * Y轴偏移
     */
    private Integer y;

    /**
     * 输出路径
     */
    private String outPath;

    private String todaySaveFileName;


    public Boolean isClip() {
        return this.isClip != null && this.isClip.equals(1);
    }
}
