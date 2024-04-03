package com.example.security;

import com.example.entities.User;
import com.example.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.example.security.TokenType.ACCESS;
import static com.example.security.TokenType.REFRESH;


@Service
public class JwtService {

    @Value("${jwt.token.access-token.secret}")
    private String accessTokenSecretKey;

    @Value("${jwt.token.refresh-token.secret}")
    private String refreshTokenSecretKey;

    @Value("${jwt.token.validity.in-millis}")
    private long jwtAccessTokenValidityInMillis;

    public String createAccessToken(User user) {
        //		Claims claims = Jwts.claims();
        Map<String, String> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("firstname", user.getFirstName());
        claims.put("lastname", user.getLastName());

        return generateToken(claims, user.getUsername(), ACCESS);
    }

    public String createRefreshToken(User user) {
        return generateToken(new HashMap<>(), user.getUsername(), REFRESH);
    }

    public String getUsername(String token) {
        return getUsername(token, ACCESS);
    }

    public String getUsername(String token, TokenType tokenType) {
        return getClaim(token, Claims::getSubject, tokenType);
        //		return Jwts.parserBuilder()
        //				.setSigningKey(getKey())
        //				.build()
        //				.parseClaimsJws(token)
        //				.getBody()
        //				.getSubject();
    }

    public boolean isTokenValid(String token) {
        return isTokenValid(token, ACCESS);
    }

    public boolean isTokenValid(String token, TokenType tokenType) {
        try {
            Date tokenExpirationDate = getClaim(token, Claims::getExpiration, tokenType);
            return tokenExpirationDate.after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            if (e.getMessage().startsWith("JWT signature does not match locally computed signature.")) {
                throw new JwtAuthenticationException("Jwt token is invalid");
            }
            throw new JwtAuthenticationException(e.getMessage());
        }
    }

    private String generateToken(Map<String, String> claims, String username, TokenType tokenType) {
        Date tokenStartDate = new Date();
        Date tokenEndDate = new Date(tokenStartDate.getTime() + jwtAccessTokenValidityInMillis);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(tokenStartDate)
                .setExpiration(tokenEndDate)
                .setSubject(username)
                .signWith(getSigningKey(tokenType), SignatureAlgorithm.HS256)
                .compact();
    }

    private <T> T getClaim(String token, Function<Claims, T> claimExtractor, TokenType tokenType) {
        final Claims claims = getAllClaims(token, tokenType);
        return claimExtractor.apply(claims);
    }

    private Key getSigningKey(TokenType tokenType) {
        String tokenSecretKey = getTokenSecret(tokenType);
        byte[] keyBytes = Decoders.BASE64.decode(tokenSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String getTokenSecret(TokenType tokenType) {
        String tokenSecretKey;
        if (tokenType == ACCESS) {
            tokenSecretKey = accessTokenSecretKey;
        } else {
            tokenSecretKey = refreshTokenSecretKey;
        }
        return tokenSecretKey;
    }
}
