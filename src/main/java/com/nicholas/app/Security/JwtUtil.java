package com.nicholas.app.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service 
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final long ACCESS_EXPIRATION = 1000*60*10;
    private static final long REFRESH_EXPIRATION = 1000*60*60*72;

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    
    public String generateAccessToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put("tokenType","accessToken");
        return createToken(claims, userDetails.getUsername());
    }

    public String generateRefreshToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put("tokenType","refreshToken");
        return createRefreshToken(claims,userDetails.getUsername());
    }
    
    private String createRefreshToken(Map<String,Object> claims,String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+REFRESH_EXPIRATION))
        .signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();

    }

    private String createToken(Map<String,Object> claims,String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+ACCESS_EXPIRATION))
        .signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){ 
        final String username = extractUsername(token); 
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
    }

    public String extractTokenType(String token){
        return extractClaim(token,Claims -> Claims.get("tokenType",String.class));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token,Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey())
        .build().parseClaimsJws(token).getBody();
    }
}
