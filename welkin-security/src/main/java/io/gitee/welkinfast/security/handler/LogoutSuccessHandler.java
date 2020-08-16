package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.security.util.ServletUtils;
import io.gitee.welkinfast.common.response.WelkinResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description  退出登录的回调
 * @Author yuanjg
 * @CreateTime 2020/08/15 14:10
 * @Version 1.0.0
 */

@Slf4j
@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // TODO 登出成功 记录登出日志
        ServletUtils.render(request, response, WelkinResult.OK("退出成功"));
    }
}