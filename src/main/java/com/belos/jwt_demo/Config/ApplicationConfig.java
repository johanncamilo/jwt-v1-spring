package com.belos.jwt_demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.belos.jwt_demo.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    
    private final UserRepository userRepository;

    /**
     * Este bean es necesario para que Spring Security pueda manejar la autenticación.
     * Proporciona un AuthenticationManager que se utiliza para autenticar las credenciales de los usuarios.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Este bean define un AuthenticationProvider que utiliza un UserDetailsService y un PasswordEncoder.
     * El UserDetailsService se encarga de cargar los detalles del usuario desde la base de datos,
     * y el PasswordEncoder se utiliza para codificar las contraseñas.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        /**
         * *Data Access Object (DAO) Authentication Provider
         * DaoAuthenticationProvider es un proveedor de autenticación que utiliza un UserDetailsService para cargar los detalles del usuario.
         * Este proveedor de autenticación se utiliza para autenticar usuarios basándose en sus credenciales
         * usa Data Access Object para autenticar usuarios usando datos de la base de datos.
         */
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailService() {
         return username -> userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
