package org.caohh.server.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.caohh.server.web.cache.ProductCache;
import org.caohh.server.web.cache.ResourceCache;
import org.caohh.server.web.model.User;
import org.caohh.server.web.tool.RedisStringTool;
import org.caohh.server.web.tool.RequestTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private RedisStringTool redisStringTool;

    @Autowired
    private RequestTool requestTool;

    @Autowired
    private ProductCache productCache;
    @Autowired
    private ResourceCache resourceCache;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        String token = requestTool.getToken();
        User user = null;
        if (redisStringTool.exists(token)) {
            String username = redisStringTool.get(token);
            user = UserController.getUser(username);
        }
        mv.addObject("user", user);
        mv.addObject("products", productCache.getProducts());
        mv.addObject("resources", resourceCache.getResources());
        return mv;
    }

}
