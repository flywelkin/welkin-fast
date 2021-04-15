package io.gitee.welkinfast.im.session;

/**
 *  session管理接口
 * @Author yuanjg
 * @CreateTime 2021/03/21 11:10
 * @Version 1.0.0
 */
public interface ServerSession {
    /**
     * 校验用户
     *
     * @return true&false
     */
    boolean isValid();

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    String getUserId();

    /**
     * 获取sessionId
     *
     * @return sessionId
     */
    String getSessionId();

    /**
     * 向用户发送数据
     *
     * @param pkg
     */
    void writeAndFlush(Object pkg);

}
