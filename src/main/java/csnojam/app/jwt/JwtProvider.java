package csnojam.app.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtProvider {

    private final Key SECRET_KEY;
    private final UserDetailsService userDetailsService;

    // 일단은 토큰 유효기간 2시간으로 해놓을게요
    private final long tokenValidTime = 1000 * 60L * 60L * 2L;

    public JwtProvider(@Value("${jwt.secret-key}") String secretKey, UserDetailsService userDetailsService) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.userDetailsService = userDetailsService;
    }

    public String createToken(UUID id){
        log.info("JWT Token 생성 시작");
        Claims claims = Jwts.claims();
        claims.put("id", id);

        JwtBuilder builder = Jwts.builder();
        builder.setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ tokenValidTime))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256);

        log.info("JWT Token 생성 완료");
        return builder.compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public void writeTokenResponse(HttpServletResponse response, String token) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("X-AUTH-TOKEN", token);
        response.setContentType("application/json;charset=UTF-8");
    }

    // 토큰에서 회원 이메일 정보 추출
    public String getUserId(String token){
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getId();
    }

    // Request Header에서 token 추출
    // "X-AUTH-TOKEN" : "TOKEN 값"
    public String getToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String token){
        try {
            return !Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration().before(new Date());
        } catch (ExpiredJwtException e){
            log.info("JWT Token 유효기간 지남");
            return false;
        }
    }
}