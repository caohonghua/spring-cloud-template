package org.caohh.server.sso.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class RequestTool {
    public static final String X_TOKEN = "X-Token";

    @Autowired
    private HttpServletRequest request;

    public String getToken() {
        return getValue(X_TOKEN);
    }

    private String getValue(String key) {
        String value = request.getHeader(key);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        return value;
    }
}
