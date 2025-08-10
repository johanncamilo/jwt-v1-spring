package com.belos.jwt_demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // cadena de filtros
        return http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para simplificar la configuración
            .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers("/auth/**").permitAll() // Permitir acceso a las rutas de autenticación
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
            ).formLogin(withDefaults()) // Configuración de inicio de sesión por defecto
            .build();
    }
}
