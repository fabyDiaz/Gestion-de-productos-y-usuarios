package cl.fabydiaz.inventario.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
   //private static final String SECRET_KEY = "clave-super-secreta-para-jwt-2025-segura!"; // al menos 32 caracteres
   //private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 horas

    private final String SECRET_KEY;
    private final long EXPIRATION_TIME;

    /*public JwtUtil(EnvLoader envLoader) {
        this.SECRET_KEY = envLoader.get("JWT_SECRET");
        this.EXPIRATION_TIME = Long.parseLong(envLoader.get("JWT_EXPIRATION"));
    }*/
    
    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expirationTime) {
        this.SECRET_KEY = secretKey;
        this.EXPIRATION_TIME = expirationTime;
    }


    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET_KEY);
    }

    public String generarToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(getAlgorithm());
    }

    public String extraerUsername(String token) {
        return getVerifier().verify(token).getSubject();
    }

    public boolean validarToken(String token, UserDetails userDetails) {
        try {
            String username = extraerUsername(token);
            return username.equals(userDetails.getUsername()) && !estaExpirado(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean estaExpirado(String token) {
        DecodedJWT jwt = getVerifier().verify(token);
        return jwt.getExpiresAt().before(new Date());
    }

    private JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }
}
