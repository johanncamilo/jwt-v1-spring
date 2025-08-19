package com.belos.jwt_demo.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.belos.jwt_demo.User.User;
import com.belos.jwt_demo.User.UserRepository;
import com.belos.jwt_demo.Jwt.JwtService;
import com.belos.jwt_demo.User.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

	public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);

        return AuthResponse.builder()
            .token(token)
            .build();
	}

    public AuthResponse register(RegisterRequest request) {
        /**
         * *PATRÓN DE DISEÑO BUILDER
         * El patrón de diseño Builder es un patrón de diseño creacional que te permite construir objetos complejos paso a paso.
         * usado aquí abajo ⬇
         */
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .country(request.getCountry())
            .role(Role.USER)
            .build();
            userRepository.save(user);

            return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    
}