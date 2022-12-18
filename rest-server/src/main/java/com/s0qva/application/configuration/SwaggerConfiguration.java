package com.s0qva.application.configuration;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@Configuration
@SecurityScheme(
        name = "token-access",
        scheme = "basic",
        type = HTTP,
        in = HEADER
)
@SecurityScheme(
        name = "api-access",
        scheme = "bearer",
        type = HTTP,
        in = HEADER
)
public class SwaggerConfiguration {
}
