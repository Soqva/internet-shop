package com.s0qva.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class ServerCorsConfiguration {
    private static final String[] ALLOWED_ORIGINS = {
            "*"
    };
    private static final String[] ALLOWED_HEADERS = {
            "*"
    };
    private static final String[] ALLOWED_METHODS = {
            "*"
    };

    @Bean
    public Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
        return corsConfigurer -> corsConfigurer.configurationSource(buildCorsConfigurationSource());
    }

    private CorsConfigurationSource buildCorsConfigurationSource() {
        return (corsConfigurationSource) -> buildCorsConfiguration();
    }

    private CorsConfiguration buildCorsConfiguration() {
        var corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(Arrays.asList(ALLOWED_ORIGINS));
        corsConfiguration.setAllowedHeaders(Arrays.asList(ALLOWED_HEADERS));
        corsConfiguration.setAllowedMethods(Arrays.asList(ALLOWED_METHODS));
        return corsConfiguration;
    }
}
