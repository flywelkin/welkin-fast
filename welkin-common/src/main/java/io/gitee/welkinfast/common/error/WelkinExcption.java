package io.gitee.welkinfast.common.error;

/**
 * 包装器业务异常类实现
 */
public class WelkinExcption extends RuntimeException implements CommonError {

    private CommonError commonError;

    //直接接受EunmBussinessError的传参，用于构造业务异常
    public WelkinExcption(CommonError commonError) {
        super(commonError.getErrorMsg());
        this.commonError = commonError;
    }

    //接受自定义errMsg的方式构造业务异常
    public WelkinExcption(CommonError commonError, String errorMsg) {
        super(errorMsg);
        this.commonError = commonError;
        this.commonError.setErrorMsg(errorMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.commonError.setErrorMsg(errorMsg);
        return this;
    }
}
