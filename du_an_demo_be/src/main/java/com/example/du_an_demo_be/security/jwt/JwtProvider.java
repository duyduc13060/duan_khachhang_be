package com.example.du_an_demo_be.security.jwt;

import com.example.du_an_demo_be.security.CustomerDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.*;
@Component
public class JwtProvider implements Serializable {
    //class nay co tac dung tao chuoi token
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    private int jwtExpiration = 86400;

    public String createToken(Authentication authentication) {
        CustomerDetailService customerDetailService = (CustomerDetailService) authentication.getPrincipal();
        return Jwts.builder().setSubject(customerDetailService.getUsername())
//                .claim(Constants.AUTHEN_INFO.AUTHORITIES_KEY, customerDetailService.getAuthorities())
//                .claim(Constants.AUTHEN_INFO.FULL_NAME,customerDetailService.getFullname())
                .setIssuedAt(new Date()).setExpiration(new Date(new Date()
                        .getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException e) {
            logger.error("valid JWT signature - Message:{}", e);
        } catch (MalformedJwtException e) {
            logger.error("the token invalid format :{}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("unsupported jwt token - Message:{}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token - Message:{}", e);
        } catch (IllegalArgumentException e) {
            logger.error("The JWT token empty - Message:{}", e);
        }
        return false;
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(token));
    }
    public String getUserNameFromToken(String token) {
        String userName = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }
}
