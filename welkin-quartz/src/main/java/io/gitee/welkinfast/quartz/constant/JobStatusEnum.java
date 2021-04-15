package io.gitee.welkinfast.quartz.constant;

/**
 *
 * @Author yuanjg
 * @CreateTime 2020/09/24 15:40
 * @Version 1.0.0
 */
public enum JobStatusEnum {


    /**
     * 0=停止
     */
    STOP("0", "停止"),
    /**
     * 1=运行
     */
    RUNNING("1", "运行");

    private final String value;
    private final String code;

    JobStatusEnum(String code, String value) {
        this.value = value;
        this.code = code;
    }

    public static JobStatusEnum getEnumByKey(String key) {
        for (JobStatusEnum e : JobStatusEnum.values()) {
            if (e.getCode().equals(key)) {
                return e;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }
}
