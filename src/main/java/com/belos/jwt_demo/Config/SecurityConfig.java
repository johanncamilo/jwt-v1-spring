package com.belos.jwt_demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.belos.jwt_demo.Jwt.JwtAuthenticationFilter;

// import static org.springframework.security.config.Customizer.withDefaults;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // cadena de filtros
        return http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para simplificar la configuración
            .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers("/auth/**").permitAll() // Permitir acceso a las rutas de autenticación
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
            )
            // .formLogin(withDefaults()) // Configuración de inicio de sesión por defecto
            // * Nuevos cambios ⬇
            .sessionManagement(sessionManager -> 
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configurar la política de sesión como sin estado
                .authenticationProvider(authProvider) // Configurar el proveedor de autenticación
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Añadir el filtro de autenticación JWT antes del filtro de autenticación por defecto
            .build();
    }
}
