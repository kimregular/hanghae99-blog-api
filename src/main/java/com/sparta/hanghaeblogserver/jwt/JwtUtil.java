package com.sparta.hanghaeblogserver.jwt;

import com.sparta.hanghaeblogserver.entity.UserRoleEnum;
import com.sparta.hanghaeblogserver.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {


    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 토큰 생성에 필요한 값
    // header 키 값

    public static final String AUTHORIZATION_KEY = "auth";
//    // 사용자 권한 값의 key

    private static final String BEARER_PREFIX = "Bearer ";
    // 토큰 식별자, 토큰을 만들 때 앞에 붙어서 생성된다.

    private static final long TOKEN_TIME = 60 * 60 * 1000L;
    // 토큰 만료시간, 밀리세컨드 기준, 60 * 1000L = 1분, *60 -> 1시간

    @Value("${jwt.secret.key}") // properties 에서 값 가져오기
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private final UserDetailsServiceImpl userDetailsService;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        // 키값이 base64로 되어있기 때문에 디코드 하는 과정, 반환값이 바이트 배열이여서 변수 타입도 그렇게 지정
        key = Keys.hmacShaKeyFor(bytes);
        // 바이트배열을 hmacShaKeyFor() 메서드를 이용해서 키 객체로 변환
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request) {
        // HttpServletRequest 객체 안에는 jwt 토큰이 들어있다.
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        // request의 헤더에 토큰이 있으므로 getHeader() 메서드로 토큰을 가져온다.
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
            // bearer 6글자 + 공백 1글자 = 7글자는 필요없다.
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
