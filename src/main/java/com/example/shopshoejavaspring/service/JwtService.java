package com.example.shopshoejavaspring.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

//    private String secretKey = "282F89C645E943F15DEA294571C5F";
//
//    public String extractUsername(String jwtToken) {
//        return extractClaim(jwtToken, Claims::getSubject);
//    }
//
//    private Claims extractAllClaims(String jwtToken) {
//        return Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(jwtToken).getBody();
//    }
//
//    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(jwtToken);
//        return claimsResolver.apply(claims);
//    }
//
//    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
//        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//                .signWith(SignatureAlgorithm.HS256, getSignInKey())
//                .compact();
//    }
//
//    public String generateToken(UserDetails userDetails) {
//        return generateToken(new HashMap<>(), userDetails);
//    }
//
//    private byte[] getSignInKey() {
//
//        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
//        return new SecretKeySpec(decodedKey , SignatureAlgorithm.HS256.getJcaName()).getEncoded();
//    }
//
//    public Boolean validateToken(String jwtToken, UserDetails userDetails) {
//        final String username = extractUsername(jwtToken);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
//    }
//
//    private boolean isTokenExpired(String jwtToken) {
//        return extractExpiration(jwtToken).before(new Date());
//    }
//
//    private Date extractExpiration(String jwtToken) {
//        return extractClaim(jwtToken, Claims::getExpiration);
//    }
}
