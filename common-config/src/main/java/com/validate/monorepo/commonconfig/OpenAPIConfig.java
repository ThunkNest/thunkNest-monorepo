package com.validate.monorepo.commonconfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenAPIConfig {
    
    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.description}")
    private String description;
    @Value("${server.port}")
    private String port;
    
    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title(title)
                .version("1.0")
                .description(description)
                .contact(new Contact()
                    .name("API Support")
                    .email("your-email@example.com")))
            .servers(Arrays.asList(
                new Server().url("http://localhost:" + port)
                    .description("Local server")
            ));
    }
} 