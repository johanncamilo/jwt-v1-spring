package com.belos.jwt_demo.Auth;
// Clase creada para la respuesta de login y register, contiene el token

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    String token;    
}