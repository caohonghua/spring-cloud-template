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
public class ProductCache {

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    @PostConstruct
    public void init() {
        try (InputStream inputStream = new ClassPathResource("json/products.json").getInputStream()) {
            StringBuilder stringBuilder = new StringBuilder();
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                stringBuilder.append(new String(bytes, 0, len));
            }
            String jsonInput = stringBuilder.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            products = objectMapper.readValue(jsonInput, new TypeReference<List<Product>>() {
            });
        } catch (IOException e) {
            log.error("初始化产品资源失败", e);
        }
    }
}
