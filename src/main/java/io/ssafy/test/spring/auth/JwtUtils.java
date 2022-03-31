package io.ssafy.test.spring.auth;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {

    private final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // @Value("${app.jwtSecret}")
    private String jwtSecret
            = "1234567890"
            + "random str"
            + "helloworld"
            + "!@#$%^&*()"
            + "abcdefghij"
            + "0987654321"
            + "1234" ; // 최소 64자리 이상

    // @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs = 1000 * 60 * 60 * 24 * 7; // 7 days

    public String generateJwt(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public Optional<String> getUsernameFromJwt(String jwt) {
        try {
            return Optional.of(Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject());
        } catch (SignatureException e) {
            logger.warn("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.warn("JWT is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("JWT is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("JWT claims string is empty: {}", e.getMessage());
        }
        return Optional.empty();
    }
}