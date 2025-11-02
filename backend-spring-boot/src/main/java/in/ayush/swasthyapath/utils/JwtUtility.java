package in.ayush.swasthyapath.utils;

import java.security.Key;
import java.util.Date;

import in.ayush.swasthyapath.enums.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtility {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username, String email, UserType userType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .claim("email", email)
                .claim("userType", userType)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) throws Exception {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    // Extracting the UserType.
    public UserType extractUserType(String token) throws Exception {
        String userType =  Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userType", String.class);
        return UserType.valueOf(userType);
    }

    // Details associated with the JWT token is claims.
    public Claims extractClaims(String token) throws Exception {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, String email, UserType userType) throws Exception{
        final String extractedUsername = extractEmail(token);
        final UserType extractedUserType = extractUserType(token);
        return extractedUsername.equals(email) && !isTokenExpired(token) && userType.equals(extractedUserType);
    }

    public boolean isTokenExpired(String token) throws Exception {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public Date getExpirationDate(String token) throws Exception {
        return extractClaims(token).getExpiration();
    }
}