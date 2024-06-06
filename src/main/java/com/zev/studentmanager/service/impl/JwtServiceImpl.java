package com.zev.studentmanager.service.impl;

import com.zev.studentmanager.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.timeout}")
    private String expiryTime;

    @Value("${jwt.timeoutRefresh}")
    private String expiryTimeRefresh;

    @Value("${jwt.securityKey}")
    private String securityKey;
    @Override
    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        return generateRefreshToken(new HashMap<>(), user);
    }

    @Override
    public String extractUser(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, UserDetails user) {
        final String username = extractUser(token);

        return username.equals(user.getUsername());
    }

    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims) // thong tin khong muon public ra ngoai(payload,...)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiryTime))) // set expiration time
                .signWith(getKey(), SignatureAlgorithm.HS256) // add signature algorithm
                .compact();
    }

    private String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims) // thong tin khong muon public ra ngoai(payload,...)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiryTimeRefresh))) // set expiration time
                .signWith(getKey(), SignatureAlgorithm.HS256) // add signature algorithm
                .compact();
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(securityKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllclaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllclaims(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
}
