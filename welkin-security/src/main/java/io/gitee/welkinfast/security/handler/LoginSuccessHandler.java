package io.gitee.welkinfast.security.handler;

import io.gitee.welkinfast.common.response.CustomResponse;
import io.gitee.welkinfast.security.LoginUserService;
import io.gitee.welkinfast.common.jwt.entity.CustomUserDetails;
import io.gitee.welkinfast.common.jwt.JwtTokenService;
import io.gitee.welkinfast.security.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 *  登录成功的处理
 * @Author yuanjg
 * @CreateTime 2020/08/15 14:03
 * @Version 1.0.0
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private JwtTokenService JwtTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User principal = (User) authentication.getPrincipal();
        CustomUserDetails customUserDetails = loginUserService.getUserByUsername(principal.getUsername());
        customUserDetails.setPassword(null);
        String jwt = JwtTokenService.createJwt(customUserDetails);
        ServletUtils.render(response, CustomResponse.OK(jwt));
    }
}
