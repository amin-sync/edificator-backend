package com.hub.edificators.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerOpenApiConfig {

    @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
            .info(new Info()
                    .title("Edificator Controller")
                    .version("v1.0")
                    .description("API documentation"));
    }
}
