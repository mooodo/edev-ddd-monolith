package com.edev.security.jwt;

import com.edev.support.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
@ConfigurationProperties(prefix = "coke.jwt")
public class JwtHelper {
    private long expire;
    private String secret;
    private String header;
    public String generateToken(String username) {
        Date now = DateUtils.getNow();
        Date expireTime = new Date(now.getTime()+expire);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimsByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt.substring(7))
                    .getBody();
        } catch (Exception e) {
            throw new JwtException("create claims error: "+e.getMessage(), e);
        }
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(DateUtils.getNow());
    }
}
