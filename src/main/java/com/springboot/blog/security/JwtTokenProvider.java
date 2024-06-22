package com.springboot.blog.security;

import com.springboot.blog.exception.BlogApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtTokenProvider  {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Generate JWT token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expireDate)
                .signWith(key())
                .compact();

    }

    // get userName from Jwt token
    public String getUsername(String token) {

         Claims claims = Jwts.parser()
                 .verifyWith(key())
                 .build().parseSignedClaims(token).getPayload();

        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token) {

        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parse(token);
            return true;
        }
        catch (MalformedJwtException ex) {
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid Jwt token");
        }
        catch (ExpiredJwtException ex) {
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Expired Jwt token");
        }
        catch (UnsupportedJwtException ex) {
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "Unsupported Jwt token");
        }
        catch (IllegalArgumentException ex) {
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }
}
