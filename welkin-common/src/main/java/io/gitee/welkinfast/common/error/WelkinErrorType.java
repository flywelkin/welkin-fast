package io.gitee.welkinfast.common.error;

public enum WelkinErrorType implements CommonError {

    //通用错误类型10000
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知错误"),
    DATA_NULL(10003, "查询结果为空"),
    DATA_UPDATE_FAIL(10004, "数据更新失败"),
    DATA_DELETE_FAIL(10005, "数据删除失败"),

    //20000开头为用户信息相关错误定义
    USER_EXIST(20001, "用户已存在"),
    USER_NOT_EXIST(20002, "用户不存在"),
    LOGIN_FAIL(20005, "用户名或密码不正确"),
    UNAUTHORIZED(20006, "未经授权"),
    LOGIN_TIMEOUT(20007, "登录超时"),
    BEYOND_MAX_LOGIN_LIMIT(20008, "账号已登录"),
    LOGIN_STATE_FAIL(20009, "登录状态错误"),
    PERMISSION_SIGNATURE_ERROR(20010,"签名失败"),
    LOGOUT_STATE_SUCCESS(20011, "退出成功");

    private int errorCode;
    private String errorMsg;

    WelkinErrorType(int errorCode, String errorMsg) {
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
    public CommonError setErrorMsg(String errMsg) {
        this.errorMsg = errMsg;
        return this;
    }
}
