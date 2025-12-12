package com.ragnar.main.Util.Helpers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtHelper {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private String jwtExpirationInMs;

    private SecretKey key;

    /*
    * Initializing the key after the class is instantiated.
    * preventing the repeated creation of the key and enhancing performance
    */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // generate token
    public String generateToken(UserDetails userDetails) {
        long expirationTime = Long.parseLong(jwtExpirationInMs);

        // get role names from the authorities list
        var roles = userDetails.getAuthorities()
                .stream()
                .map(x -> x.getAuthority())
                .toList();

        return Jwts.builder()
                .claim("roles", roles)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // extract roles from the jwt token
    public List<String> extractRoles(String token) {
        var claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        var roles = claims.get("roles", List.class);
        return roles;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        if (username.equals(userDetails.getUsername())) {
            return true;
        }
        return false;
    }
}
