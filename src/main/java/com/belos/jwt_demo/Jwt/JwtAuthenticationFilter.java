package com.belos.jwt_demo.Jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
/**
 * OncePerRequestFilter clase abstracta para crear fitros personalizados
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * * doFilterInternal método que se ejecuta una vez por cada petición
     * Este método se encarga de interceptar las peticiones HTTP y realizar la lógica de autenticación JWT.
     * @param request la solicitud HTTP entrante
     * @param response la respuesta HTTP que se enviará al cliente
     * @param filterChain la cadena de filtros que se ejecutará después de este filtro
     * @throws ServletException si ocurre un error durante el procesamiento del filtro
     * @throws IOException si ocurre un error de entrada/salida durante el procesamiento del filtro
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                // todo obtener el token del request
                final String token = getTokenFomRequest(request);

                if(token == null) {
                    filterChain.doFilter(request, response); // si no hay token, continuamos con la cadena de filtros
                    return;
                }
                filterChain.doFilter(request, response); // si hay token, continuamos con la cadena de filtros

                // * Ideas de copilot
                // Aquí podrías agregar la lógica para validar el token y establecer la autenticación en el contexto de seguridad
                // Por ejemplo, podrías usar un JwtService para validar el token y establecer el usuario autenticado en el contexto de seguridad.
                // JwtService jwtService = ...;
                // UserDetails userDetails = jwtService.validateTokenAndGetUserDetails(token);
                // if (userDetails != null) {
                //     UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                //         userDetails, null, userDetails.getAuthorities());
                //     SecurityContextHolder.getContext().setAuthentication(authentication);
                // }
                // Si el token es válido, puedes continuar con la cadena de filtros.
                // Si el token no es válido, puedes enviar una respuesta de error o redirigir a una página de error.
                // Por ejemplo, podrías enviar una respuesta de error 401 Unauthorized:
                // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                // O redirigir a una página de error:
                // response.sendRedirect("/error");
                // Nota: Asegúrate de manejar adecuadamente los casos en los que el token sea nulo o inválido para evitar problemas de seguridad.

    }

    private String getTokenFomRequest(HttpServletRequest request) {
        // obtenemos el header
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // Authorization es el nombre del header que contiene el token JWT

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")) { // Verificamos que el header no esté vacío y que comience con "Bearer"
            return authHeader.substring(7); // Extraemos el token JWT del header, eliminando el prefijo "Bearer "
        }
        return null;
    }
    
}
