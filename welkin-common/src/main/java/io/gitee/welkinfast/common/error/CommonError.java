package io.gitee.welkinfast.common.error;

public interface CommonError {
    int getErrorCode();
    String getErrorMsg();
    CommonError setErrorMsg(String errMsg);
}
