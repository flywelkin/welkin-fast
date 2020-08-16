package io.gitee.welkinfast.common.response;

/**
 * JSON格式统一返回类
 */
public class WelkinResult {
    public static final int SUCCESS = 200;
    public static final int FAIL = 500;

    private Integer code = SUCCESS;
    private Object data;

    public static WelkinResult OK(Object data) {
        return new WelkinResult(WelkinResult.SUCCESS, data);
    }

    public static WelkinResult CREAT(Integer code, Object data) {
        return new WelkinResult(code, data);
    }

    public WelkinResult() {
    }

    public WelkinResult(Integer code) {
        this.code = code;
    }

    public WelkinResult(Object data) {
        this.data = data;
    }

    public WelkinResult(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
