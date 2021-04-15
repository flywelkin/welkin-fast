package io.gitee.welkinfast.common.response;

/**
 *  JSON格式统一返回类
 * @Author yuanjg
 * @CreateTime 2020/8/22 12:30
 * @Version 1.0.0
 */
public class CustomResponse<T> {

    public static final int SUCCESS = 200;
    public static final int FAIL = 500;

    private Integer code = CustomResponse.SUCCESS;
    private String message;
    private T data;

    public static <T> CustomResponse<T> OK(T data) {
        return new CustomResponse<T>(CustomResponse.SUCCESS, null, data);
    }

    public static <T> CustomResponse<T> FAIL(String message) {
        return new CustomResponse<T>(CustomResponse.FAIL, message, null);
    }

    public static <T> CustomResponse<T> FAIL(Integer code, String message) {
        return new CustomResponse<T>(code, message, null);
    }

    public CustomResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
