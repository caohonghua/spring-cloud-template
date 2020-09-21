package org.caohh.server.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/loginPage")
    public ModelAndView loginPage(String url, String message) {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("url", url);
        mv.addObject("message", message);
        return mv;
    }

    @PostMapping("/login")
    public String login(String username, String password, String url, HttpSession session, HttpServletResponse response) {
        if ("admin".equals(username)) {
            String token = "admin" + System.currentTimeMillis();
            Cookie cookie = new Cookie("accessToken", token);
            response.addCookie(cookie);
            session.setAttribute(token, "");
            return StringUtils.isEmpty(url)
                    ? "redirect:http://localhost:9002"
                    : "redirect:" + url;
        } else {
            return "redirect:http://localhost:9002/sso/user/loginPage?message=用户名或密码错误&url=" + url;
        }
    }

    @GetMapping("/exists/{token}")
    public Boolean exists(@PathVariable String token, HttpSession session) {
        return session.getAttribute(token) != null;
    }

}
