package com.klef.soa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig
{
    @Bean
    public OpenAPI customOpenAPI()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("Book Entity Project")
                        .version("1.0")
                        .description("REST API Documentation for Entity Task")
                        .contact(new Contact()
                                .name("Nischal Singana")
                                .email("singananischal@gmail.com")));
    }
}