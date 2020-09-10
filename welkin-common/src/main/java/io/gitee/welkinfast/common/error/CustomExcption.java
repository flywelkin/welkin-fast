package io.gitee.welkinfast.common.error;

/**
 * 包装器业务异常类实现
 */
public class CustomExcption extends RuntimeException implements CustomError {

    private CustomError customError;

    //直接接受EunmBussinessError的传参，用于构造业务异常
    public CustomExcption(CustomError customError) {
        super(customError.getErrorMsg());
        this.customError = customError;
    }

    //接受自定义errMsg的方式构造业务异常
    public CustomExcption(CustomError customError, String errorMsg) {
        super(errorMsg);
        this.customError = customError;
        this.customError.setErrorMsg(errorMsg);
    }

    @Override
    public int getErrorCode() {
        return this.customError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.customError.getErrorMsg();
    }

    @Override
    public CustomError setErrorMsg(String errorMsg) {
        this.customError.setErrorMsg(errorMsg);
        return this;
    }
}
