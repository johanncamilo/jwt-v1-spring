package com.belos.jwt_demo.Jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    /**
     * *MÉTODO ORIGINAL
     * Este método genera un token JWT para un usuario específico.
     * Este servicio es llamado por el AuthService para generar un token al registrar un usuario.
     * @param user el usuario para el cual se generará el token
     * @return el token JWT generado
     */
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    /**
     * *MÉTODO SOBRECARGADO
     * Este método genera un token JWT con reclamos adicionales.
     * @param extraClaims un mapa de reclamos adicionales que se incluirán en el token
     * @param user el usuario para el cual se generará el token
     * @return el token JWT generado
     * Este metodo se llama igual que el anterior, pero con un mapa de reclamos adicionales,
     * lo que permite agregar información adicional al token JWT.
     * Por ejemplo, podrías agregar información como el ID del usuario, roles adicionales, etc
     */
    private String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(user.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+1000*60*24)) // *se le suma un dia
            // .signWith(SignatureAlgorithm.HS256, getKey()) // !deprecado
            // .signWith(getKey(), Jwts.SIG.HS256) // !no funciona
            .signWith(getKey())
            .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY); // decodifica la clave secreta en base64

        // * Keys.hmacShaKeyFor es un método de la librería jjwt que crea una clave HMAC a partir de un arreglo de bytes.
        // Esta clave se utiliza para firmar y verificar los tokens JWT.
        // La clave debe ser lo suficientemente larga y segura para garantizar la integridad y autenticidad del token.
        // En este caso, se utiliza una clave de 64 bytes, que es adecuada para el algoritmo HMAC SHA-256.
        // Si la clave es demasiado corta, se lanzará una excepción IllegalArgumentException.
        // Si la clave es demasiado larga, se truncará a 64 bytes.
        // Es importante mantener la clave secreta
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        // return Jwts
        //         .parser()
        //         .setSigningKey(getKey())
        //         .build()
        //         .parseClaimsJws(token)
        //         .getBody();
        return Jwts
                .parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
