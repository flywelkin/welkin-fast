package io.gitee.welkinfast.common.error;

public enum CustomErrorType implements CustomError {

    //认证相关错误
    FORBIDDEN(401, "未经认证"),
    UNAUTHORIZED(403, "未经授权"),

    //通用错误类型10000
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知错误"),
    DATA_NULL(10003, "查询结果为空"),
    DATA_SAVE_FAIL(10004, "数据保存失败"),
    DATA_UPDATE_FAIL(10005, "数据更新失败"),
    DATA_DELETE_FAIL(10006, "数据删除失败"),

    //20000开头为用户信息相关错误定义
    USER_EXIST(20001, "用户已存在"),
    USER_NOT_EXIST(20002, "用户不存在"),
    LOGIN_FAIL(20005, "用户名或密码不正确"),

    MINI_ERROR(30000, "小程序接口请求异常"),
    FILE_NOT_EXIST(30001, "文件不存在"),
    CONVERSION_ERROR(30002, "转换异常"),
    TASK_ERROR(40000, "定时任务异常");

    private int errorCode;
    private String errorMsg;

    CustomErrorType(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public CustomError setErrorMsg(String errMsg) {
        this.errorMsg = errMsg;
        return this;
    }
}
