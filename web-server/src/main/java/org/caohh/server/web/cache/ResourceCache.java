package org.caohh.server.web.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Slf4j
public class ResourceCache {
    private List<Resource> resources;

    public List<Resource> getResources() {
        return resources;
    }

    @PostConstruct
    public void init() {
        try (InputStream inputStream = new ClassPathResource("json/resources.json").getInputStream()) {
            StringBuilder stringBuilder = new StringBuilder();
            byte[] bytes = new byte[2048];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                stringBuilder.append(new String(bytes, 0, len));
            }
            String jsonInput = stringBuilder.toString();
            ObjectMapper mapper = new ObjectMapper();
            resources = mapper.readValue(jsonInput, new TypeReference<List<Resource>>() {
            });
        } catch (IOException e) {
            log.error("初始化资源失败", e);
        }
    }
}
