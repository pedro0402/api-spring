package com.pedro.jpa.libraryapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Library API",
                contact = @Contact(
                        name = "Pedro Moraes",
                        url = "https://github.com/pedro0402"
                )
        ))
public class OpenApiConfiguration {
}
