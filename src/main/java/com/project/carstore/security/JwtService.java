package com.project.carstore.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService{
    private  final String SECRET_KEY="95e2a5ceaf752aba9fd66ff6ca4aaf8031bd0306f8ad48ec57fdf0770a82f21d";

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user)
    {
        String username=extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token)
    {
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }


    public <T> T extractClaim(String token, Function<Claims,T> resolver)
    {
        Claims claims=extractAllClaims(token);
        return resolver.apply(claims);
    }


    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user){
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .signWith(getSigninKey())
                .compact();

    }
    private SecretKey getSigninKey()
    {
        byte[] keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }


}
