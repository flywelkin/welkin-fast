package io.gitee.welkinfast.im.message;

/**
 * 返回码枚举
 *
 * @Author yuanjg
 * @CreateTime 2021/03/23 16:10
 * @Version 1.0.0
 */
public enum ResponseEnmuType {

    SUCCESS(0, "Success"),
    AUTH_FAILED(1, "登录失败"),
    NO_TOKEN(2, "没有授权码"),
    UNKNOW_ERROR(3, "未知错误"),
    TOKEN_EXPIRED(3, "认证过期");

    private Integer code;
    private String desc;

    ResponseEnmuType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
