package com.example.educat_be.AuthenticationAndAuthorisation;

import com.example.educat_be.DAO.TokenDAO;
import com.example.educat_be.DTO.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TokenDAO tokenDAO;


    private SecretKey key() {
        byte[] decodedSecret = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(decodedSecret);
    }


    @Transactional
    public TokenDTO generateToken(UserDetails userDetails) {
        String accessToken = generateAccessToken(userDetails.getUsername());
        String refreshToken = generateRefreshToken(userDetails.getUsername());

        tokenDAO.removeToken(userDetails.getUsername());
        saveTokenToDatabase(userDetails.getUsername(), accessToken, refreshToken);

        return new TokenDTO(accessToken, refreshToken);
    }

    public String generateAccessToken(String username) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + 1 * 60 * 60 * 1000); // 1 hour

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key())
                .compact();
    }

    public String generateRefreshToken(String username) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + 24 * 60 * 60 * 1000); // 24 hours

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key())
                .compact();
    }

    public boolean validateRefreshToken(String token) {
        return !isTokenExpired(token) ;
    }


    private void saveTokenToDatabase(String username, String accesstoken,String refreshtoken) {
        String sql = "INSERT INTO token (email, access_token,refresh_token) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, username, accesstoken,refreshtoken);
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims= Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }

    private Date extractExpiration(String token) {
        Claims claims=Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
        return claims.getExpiration();
    }


    public String getuserfromtoken(String token) {
        Claims claims=Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }

}
