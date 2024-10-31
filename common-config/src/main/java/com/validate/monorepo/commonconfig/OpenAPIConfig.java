package com.validate.monorepo.commonconfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Authentication Service API")
                .version("1.0")
                .description("Authentication Service API Documentation")
                .contact(new Contact()
                    .name("API Support")
                    .email("your-email@example.com")))
            .servers(Arrays.asList(
                new Server().url("http://localhost:2020")
                    .description("Local server")
            ));
    }
} 