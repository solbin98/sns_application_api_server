package com.practice.chatapiserver.security.authenticate.jwt;

import com.practice.chatapiserver.ErrorCodeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws  ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token == null){
            filterChain.doFilter(request, response);
        }
        else{
            try{
                token = token.replaceAll("Bearer ", "");
                JwtTokenManager.validateToken(token);
                Claims claims = JwtTokenManager.getClaimsFromToken(token);
                request.setAttribute("member_id", claims.get("member_id"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(claims.get("username"),
                        null, (Collection<? extends GrantedAuthority>) claims.get("authority"));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
            catch (MalformedJwtException e) {
                throw new MalformedJwtException(ErrorCodeEnum.REJECT_TOKEN.getMsg());
            } catch (SignatureException e) {
                throw new SignatureException(ErrorCodeEnum.INVALID_TOKEN.getMsg());
            } catch (ExpiredJwtException e) {
                throw new RuntimeException(ErrorCodeEnum.REJECT_TOKEN.getMsg());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(ErrorCodeEnum.INVALID_ARGUMENT.getMsg());
            } catch (ParseException e){
                throw new RuntimeException(ErrorCodeEnum.PARSING_ERROR.getMsg());
            } catch (NullPointerException e){
                throw new NullPointerException(ErrorCodeEnum.TOKEN_EMPTY.getMsg());
            } catch (Exception e){
                throw new RuntimeException(ErrorCodeEnum.SERVER_INNER_ERROR.getMsg());
            }
        }
    }
}
