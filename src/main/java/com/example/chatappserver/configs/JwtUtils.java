package com.example.chatappserver.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    private final String path = System.getProperty("user.dir") + "/src/main/resources/static/key.txt";
    private final String algorithm = "RSA";

    public PrivateKey createPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        int keySize = 2048;
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] secretKeyBytes = keyPair.getPrivate().getEncoded();
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(secretKeyBytes);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }


    public String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        long expirationTime = 60 * 60 * 1000L;
        Date expiryTime = new Date(now.getTime() + expirationTime);
        PrivateKey privateKey = null;
        try {
            privateKey = createPrivateKey();
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(privateKey.getEncoded());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(expiryTime).signWith(privateKey, SignatureAlgorithm.RS256).compact();
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        PrivateKey privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(path))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Jwts.parserBuilder().setSigningKey(privateKey).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExprired(String token) {
        Date expiration = extractExpirationDate(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExprired(token);
    }
}
