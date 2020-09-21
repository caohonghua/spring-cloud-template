package org.caohh.server.sso.controller;

import lombok.extern.slf4j.Slf4j;
import org.caohh.common.model.Code;
import org.caohh.common.model.Result;
import org.caohh.server.sso.client.UserClient;
import org.caohh.server.sso.tool.RequestTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Value("${domain.url}")
    private String domain;

    @Value("${token.duration}")
    private Integer duration;

    @Value("${gateway.url}")
    private String gatewayUrl;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RequestTool requestTool;

    @PostMapping("/")
    @ResponseBody
    public Result login(HttpServletResponse response, String username, String password) throws IOException {
        String token = requestTool.getToken();
        Result result;
        if (token != null && userClient.tokenExists(token)) {
            result = new Result(Code.SUCCESS, "用户已登陆", gatewayUrl);
        } else {
            result = userClient.login(username, password);
            if (Code.SUCCESS.getValue().equals(result.getCode())) {
                Cookie cookie = new Cookie(RequestTool.X_TOKEN, result.getData().toString());
                cookie.setDomain(domain);
                cookie.setPath("/");
                cookie.setMaxAge(60 * duration);
                response.addCookie(cookie);
                result.setData(gatewayUrl);
            }
        }
        return result;
    }

    @GetMapping("/page")
    public String loginPage() {
        return "login";
    }
}
