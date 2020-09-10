package io.gitee.welkinfast.common.error;

public interface CustomError {
    int getErrorCode();
    String getErrorMsg();
    CustomError setErrorMsg(String errMsg);
}
