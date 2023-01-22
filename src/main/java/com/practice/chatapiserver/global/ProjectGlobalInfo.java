package com.practice.chatapiserver.global;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

public class ProjectGlobalInfo {
    public final static String JWT_SECRET_KEY = "ZXCQW12DNAXC123RDdwdawadwdaadw123dq12213dX";
    public final static Long JWT_ACCESS_EXPIRED_TIME = 60*60*24*30*1000L; // 30일 동안 임시로.. 추후에 refresh 토큰
    public final static Long JWT_REFRESH_EXPIRED_TIME = 60*60*24*1000L;
}
