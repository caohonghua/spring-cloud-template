package org.caohh.server.web.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisStringTool {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean exists(String key) {
        return key != null && stringRedisTemplate.hasKey(key);
    }

    public String get(String key) {
        return key == null ? null : stringRedisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value, Duration duration) {
        stringRedisTemplate.opsForValue().set(key, value, duration);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
}
