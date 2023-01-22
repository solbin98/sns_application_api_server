package com.practice.chatapiserver.security.authenticate.jwt;

import com.practice.chatapiserver.ErrorCodeEnum;
import com.practice.chatapiserver.global.ProjectGlobalInfo;
import com.practice.chatapiserver.security.authenticate.CustomUserDetail;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


public class JwtTokenManager {
    private final static String secretKey = ProjectGlobalInfo.JWT_SECRET_KEY;
    private final static Long accessExpireTime = ProjectGlobalInfo.JWT_ACCESS_EXPIRED_TIME;
    private final static Long refreshExpireTime = ProjectGlobalInfo.JWT_REFRESH_EXPIRED_TIME;
    private final static SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    //  body {
    //  authority : ROLE_USER, ROLE_ADMIN ...
    //  subject : username
    //  expiration : ....
    //  issued_at : ....
    //  }
    public static String createToken(Authentication authentication, Long expiredTime){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiredTime);
        Claims claims = Jwts.claims();
        claims.put("authority", authentication.getAuthorities());
        claims.put("member_id", ((CustomUserDetail)authentication.getPrincipal()).getMember().getId()); // Member Entity에 의존하게 되는 단점..
        claims.setSubject((String)((UserDetails)authentication.getPrincipal()).getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate);

        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims getClaimsFromToken(String token) throws ParseException {
        Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(token);
        return (Claims) jwt.getBody();
    }

    public static String createAccessToken(Authentication authentication){
        return createToken(authentication, accessExpireTime);
    }

    public static String createRefreshToken(Authentication authentication){
        return createToken(authentication, refreshExpireTime);
    }

    public static void validateToken(String token) throws MalformedJwtException, SignatureException, ExpiredJwtException, IllegalArgumentException, ParseException {
        Jwt jwt = Jwts.parserBuilder().setSigningKey(key).build().parse(token);
        Claims claims = (Claims) jwt.getBody();
        Date expiration = claims.getExpiration();
        if(new Date().after(expiration)){
            throw new ExpiredJwtException(jwt.getHeader(), (Claims) jwt.getBody(), ErrorCodeEnum.EXPIRED_TOKEN.getMsg());
        }
    }
}
