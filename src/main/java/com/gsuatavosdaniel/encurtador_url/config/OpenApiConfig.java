package com.gsuatavosdaniel.encurtador_url.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customAPI(){

        return new OpenAPI()
                .info(new Info()
                        .title("Encurtador URL")
                        .version("1.o")
                        .description("API para encurtamento de URLs"));
    }
}
