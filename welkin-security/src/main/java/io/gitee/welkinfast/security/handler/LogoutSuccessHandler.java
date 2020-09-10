package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.security.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 退出登录的回调
 * @Author yuanjg
 * @CreateTime 2020/08/15 14:10
 * @Version 1.0.0
 */
@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(LogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ServletUtils.render(response, CustomResponse.OK("退出成功"));
    }
}
