package org.caohh.server.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableMap;
import org.caohh.common.model.Code;
import org.caohh.common.model.Result;
import org.caohh.server.web.model.User;
import org.caohh.server.web.tool.RedisStringTool;
import org.caohh.server.web.tool.RequestTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Map<String, User> user = new ConcurrentHashMap<>();//充当数据库存储用户数据

    static {
        //给定默认用户admin/admin
        user.put("admin", new User("admin", "admin"));
    }

    @Value("${sso.url}")
    private String ssoUrl;
    @Value("${token.duration}")
    private Long duration;
    @Value("${gateway.url}")
    private String gatewayUrl;

    @Autowired
    private RedisStringTool redisStringTool;

    @Autowired
    private RequestTool requestTool;

    private static boolean userReg(String username, String password) {
        if (user.containsKey(username)) {
            return false;
        } else {
            user.put(username, new User(username, password));
            return true;
        }
    }

    public static User getUser(String username) {
        return user.get(username);
    }

    @GetMapping("/regPage")
    public String regPage() {
        return "user/reg";
    }

    @PostMapping("/reg")
    @ResponseBody
    public Result reg(String username, String password) {
        return userReg(username, password)
                ? new Result(Code.SUCCESS, "注册成功", ImmutableMap.of("href", ssoUrl))
                : new Result(Code.FAIL, "注册失败,用户已存在");
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(String username, String password) throws JsonProcessingException {
        Result result;
        User user = checkLogin(username, password);
        if (user != null) {
            String token = username + System.currentTimeMillis();
            redisStringTool.set(token, username, Duration.ofMinutes(duration));
            result = new Result(Code.SUCCESS, "登陆成功", token);
        } else {
            result = new Result(Code.FAIL, "用户名或密码错误");
        }
        return result;
    }

    @GetMapping("/logout")
    public String logout() {
        String token = requestTool.getToken();
        if (redisStringTool.exists(token)) {
            redisStringTool.delete(token);
        }
        return "redirect:" + gatewayUrl;
    }

    @GetMapping("/tokenExists")
    @ResponseBody
    public Boolean tokenExists(String token) {
        return redisStringTool.exists(token);
    }

    private User checkLogin(String username, String password) {
        return user.containsKey(username) && password.equals(user.get(username).getPassword())
                ? user.get(username) : null;
    }
}
