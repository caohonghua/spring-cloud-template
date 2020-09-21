package org.caohh.server.sso.client;

import org.caohh.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "web-server", path = "/user")
public interface UserClient {
    @GetMapping("/tokenExists")
    Boolean tokenExists(@RequestParam String token);

    @PostMapping("/login")
    Result login(@RequestParam String username, @RequestParam String password);

}
