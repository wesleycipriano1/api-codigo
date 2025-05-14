package api_code.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Gera o token JWT
    public String gerarToken(String email, Long id) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", id) // Inclui o ID no token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtém o email do token
    public String obterEmail(String token) {
        try {
            return getClaims(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    // Obtém o ID do token
    public Long obterId(String token) {
        try {
            Claims claims = getClaims(token);
            Object id = claims.get("id");
            return id != null ? Long.valueOf(id.toString()) : null;
        } catch (Exception e) {
            return null;
        }
    }

    // Verifica se o token é válido
    public boolean tokenValido(String token) {
        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            return expirationDate.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Obtém as claims do token
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
