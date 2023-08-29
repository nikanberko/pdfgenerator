package com.properbooker.pdfgenerator.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.function.Function;

@Component
public class TokenProvider implements Serializable {
    private String generatedToken;

    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(final String token) {
        System.out.println(token);
        return Jwts.parser()
                .setSigningKey("secret-key".getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
