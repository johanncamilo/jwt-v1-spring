package com.belos.jwt_demo.Auth;

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

	public AuthResponse login(LoginRequest request) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'login'");
	}

    public AuthResponse register(RegisterRequest request) {
        /**
         * *PATRÓN DE DISEÑO BUILDER
         * El patrón de diseño Builder es un patrón de diseño creacional que te permite construir objetos complejos paso a paso.
         * usado aquí abajo ⬇
         */
        User user = User.builder()
            .username(request.getUsername())
            .password(request.getPassword())
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