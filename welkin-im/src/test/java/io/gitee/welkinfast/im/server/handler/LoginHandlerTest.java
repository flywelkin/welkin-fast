package io.gitee.welkinfast.im.server.handler;

import io.gitee.welkinfast.im.proto.ImEntity;
import io.gitee.welkinfast.im.server.handler.sharable.LoginHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 *
 * @Author yuanjg
 * @CreateTime 2021/03/21 09:21
 * @Version 1.0.0
 */
@SpringBootTest
public class LoginHandlerTest {

    @Test
    public void test() {
        ImEntity.Message message = ImEntity.Message.newBuilder()
                .setType(ImEntity.HeadType.LOGIN_REQUEST)
                .setSessionId("sessionId")
                .setId(UUID.randomUUID().toString().replaceAll("-", ""))
                .buildPartial();
        ImEntity.LoginRequest.Builder loginRequest = ImEntity.LoginRequest.newBuilder();
        loginRequest.setToken("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJPV1ZrTlRSbE1qRXROakJrTVMwMFltVmtMV0ppTkRrdE56QmlOV1ptTVRjNU1XVXciLCJ1c2VyX2luZm8iOiJ7XCJhZG1pblwiOmZhbHNlLFwiaWRcIjpcIjU5NDhiODdhLTllZGMtNGZlYS04N2YyLWY1NmMxMmIxNDFiOFwiLFwicGVybWlzc2lvbnNcIjpbXCJzeXM6bWVudTp2aWV3XCIsXCJzeXM6cm9sZTp2aWV3XCIsXCJzeXM6dXNlcjp2aWV3XCIsXCJzeXM6ZGVwdDp2aWV3XCIsXCJ0YXNrOnF1YXJ0ejpsaXN0XCIsXCJzeXNcIixcInN5czp1c2VyOmxpc3RcIixcInN5czpyb2xlOmFkZFwiLFwic3lzOm1lbnU6YWRkXCIsXCJ0YXNrXCIsXCJzeXM6dXNlcjphZGRcIixcInN5czpkZXB0OmFkZFwiLFwic3lzOnJvbGU6bGlzdFwiLFwic3lzOmRlcHQ6dXBkYXRlXCIsXCJzeXM6bWVudTp1cGRhdGVcIixcInN5czptZW51Omxpc3RcIixcInN5czp1c2VyOnVwZGF0ZVwiLFwic3lzOmRlcHQ6ZGVsZXRlXCIsXCJzeXM6cm9sZTp1cGRhdGVcIixcInN5czptZW51OmRlbGV0ZVwiLFwic3lzOmRlcHQ6bGlzdFwiLFwic3lzOnJvbGU6ZGVsZXRlXCIsXCJzeXM6dXNlcjpkZWxldGVcIl0sXCJyb2xlc1wiOltcImFkbWluXCJdLFwidXNlcm5hbWVcIjpcIndlbGtpblwifSIsImV4cCI6MTYxNzE3MTMwNX0.WfWlZRmcwstCTrWufgni4NxYXiUEL-XVsgqz0FTGQHs");
        ImEntity.Message build = message.toBuilder().setLoginRequest(loginRequest).build();
        String s = message.toString();
        EmbeddedChannel channel = new EmbeddedChannel(new LoginHandler());
        channel.writeInbound(build);
        channel.finish();
    }

    public static void main(String[] args) {
        ImEntity.Message message = ImEntity.Message.newBuilder()
                .setType(ImEntity.HeadType.LOGIN_REQUEST)
                .setSessionId("sessionId")
                .setId(UUID.randomUUID().toString().replaceAll("-", ""))
                .buildPartial();
        ImEntity.LoginRequest.Builder loginRequest = ImEntity.LoginRequest.newBuilder();
        loginRequest.setToken("qqqq");
        ImEntity.Message build = message.toBuilder().setLoginRequest(loginRequest).build();
        String s = message.toString();
        String s1 = message.getLoginRequest().toString();
        System.out.println(s);
    }

}
