package org.caohh.server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tools")
public class ToolController {
    @GetMapping("/")
    public String index() {
        return "tool/index";
    }
}
