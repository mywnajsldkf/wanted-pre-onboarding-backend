package com.example.board.util;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${jwt.password}")
    private String secretKey;

    public String createAuthToken(String email) {
        return create(email, "authToken", 1);
    }

    public String createRefreshToken(String email) {
        return create(email, "refreshToken", 2);
    }

    private String create(String email, String subject, Integer hours) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + hours * Duration.ofHours(4).toMillis());
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .compact();
    }

    public String parseJwtToken(String token) {
        String payloadJWT = token.split("\\.")[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();

        final String payload = new String(decoder.decode(payloadJWT));
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> jsonArray = jsonParser.parseMap(payload);
        String email = jsonArray.get("email").toString();

        return email;
    }

    private String removeBearer(String token) {
        return token.substring("Bearer".length());
    }
}
