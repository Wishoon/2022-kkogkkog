package com.woowacourse.kkogkkog.auth.infrastructure.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private static final String ACCESS_TOKEN_TYPE = "Bearer";
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";

    private final TokenExtractor tokenExtractor;
    private final Key secretKey;
    private final long validityInMilliseconds;

    public TokenProvider(final TokenExtractor tokenExtractor,
                         @Value("${security.jwt.secret-key}") final String secretKey,
                         @Value("${security.jwt.expire-length}") final long validityInMilliseconds) {
        this.tokenExtractor = tokenExtractor;
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createAccessToken(final Long id, final boolean approval) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setSubject(ACCESS_TOKEN_SUBJECT)
            .setIssuedAt(now)
            .setExpiration(validity)
            .claim("id", id)
            .claim("approval", approval)
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isValidToken(final String authorizationHeader) {
        String token = tokenExtractor.extractToken(authorizationHeader, ACCESS_TOKEN_TYPE);
        try {
            final Jws<Claims> claims = getClaimsJws(token);
            return isAccessToken(claims) && isNotExpired(claims);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public MemberPayload getPayload(final String accessToken) {
        Claims body = getClaimsJws(accessToken).getBody();
        try {
            Long memberId = body.get("id", Long.class);
            Boolean approval = body.get("approval", Boolean.class);
            return new MemberPayload(memberId, approval);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Jws<Claims> getClaimsJws(final String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token);
    }

    private boolean isAccessToken(final Jws<Claims> claims) {
        return claims.getBody()
            .getSubject()
            .equals(ACCESS_TOKEN_SUBJECT);
    }

    private boolean isNotExpired(final Jws<Claims> claims) {
        return claims.getBody()
            .getExpiration()
            .after(new Date());
    }
}
