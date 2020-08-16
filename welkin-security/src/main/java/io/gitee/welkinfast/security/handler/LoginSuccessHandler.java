package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.common.response.WelkinResult;
import io.gitee.welkinfast.security.LoginUserService;
import io.gitee.welkinfast.security.entity.DefaultUserDetails;
import io.gitee.welkinfast.security.jwt.JwtTokenService;
import io.gitee.welkinfast.security.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 登录成功的处理
 * @Author yuanjg
 * @CreateTime 2020/08/15 14:03
 * @Version 1.0.0
 */

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private JwtTokenService JwtTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User principal = (User) authentication.getPrincipal();
        DefaultUserDetails defaultUserDetails = loginUserService.getUserByUsername(principal.getUsername());
        String jwt = JwtTokenService.createJwt(defaultUserDetails);
        // TODO 登录成功 记录日志
        ServletUtils.render(request, response, WelkinResult.OK(jwt));
    }
}
