package com.unsis.dba_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        // Permitir orígenes específicos (ajusta según tu frontend)
        corsConfig.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // Si prefieres orígenes específicos, usa:
        // corsConfig.setAllowedOrigins(Arrays.asList(
        //     "http://localhost:4200",
        //     "http://localhost:8100",
        //     "http://localhost",
        //     "http://127.0.0.1"
        // ));
        
        // Permitir todos los métodos HTTP
        corsConfig.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        
        // Permitir todos los headers
        corsConfig.setAllowedHeaders(List.of("*"));
        
        // Permitir credenciales
        corsConfig.setAllowCredentials(true);
        
        // Tiempo de caché para preflight requests
        corsConfig.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        
        return new CorsWebFilter(source);
    }
}
