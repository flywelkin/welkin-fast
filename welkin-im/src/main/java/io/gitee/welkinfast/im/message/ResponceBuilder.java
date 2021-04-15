package io.gitee.welkinfast.im.message;

import io.gitee.welkinfast.im.proto.ImEntity;
import org.springframework.stereotype.Component;

/**
 * 登录返回信息构造器
 *
 * @Author yuanjg
 * @CreateTime 2021/03/23 16:05
 * @Version 1.0.0
 */
@Component
public class ResponceBuilder {

    public ImEntity.Message loginOk(ResponseEnmuType enmuType, String seqId, String sessionId) {
        ImEntity.Message.Builder message = ImEntity.Message.newBuilder()
                .setType(ImEntity.HeadType.LOGIN_RESPONSE)
                .setId(seqId)
                .setSessionId(sessionId);

        ImEntity.LoginResponse.Builder loginResponse = ImEntity.LoginResponse.newBuilder()
                .setSuccess(true)
                .setExpose(1);
        message.setLoginResponse(loginResponse.build());

        return message.build();
    }

    public ImEntity.Message loginError(ResponseEnmuType enmuType, String seqId, String sessionId) {
        ImEntity.Message.Builder message = ImEntity.Message.newBuilder()
                .setType(ImEntity.HeadType.LOGIN_RESPONSE)
                .setId(seqId)
                .setSessionId(sessionId);

        ImEntity.LoginResponse.Builder loginResponse = ImEntity.LoginResponse.newBuilder()
                .setSuccess(false)
                .setCode(enmuType.getCode())
                .setInfo(enmuType.getDesc())
                .setExpose(1);

        message.setLoginResponse(loginResponse.build());

        return message.build();
    }
}
