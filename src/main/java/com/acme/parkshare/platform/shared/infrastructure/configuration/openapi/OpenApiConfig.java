package com.acme.parkshare.platform.shared.infrastructure.configuration.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI parkShareOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ParkShare Platform API")
                        .description("RESTful API for the ParkShare parking management platform")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}
