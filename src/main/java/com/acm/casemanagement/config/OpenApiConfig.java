package com.acm.casemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()

                .info(new Info().title("Application API")
                        .version("v1")
                        .description("API documentation for Application"));
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}


