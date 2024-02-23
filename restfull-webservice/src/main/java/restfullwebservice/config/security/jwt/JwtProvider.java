package restfullwebservice.config.security.jwt;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	public static final String jwtSecret= "j+1JxmUT4d2TwJ9Ep5AufZYvdOiEzzZMOi1KMZK4tnw="; 
	//https://www.devglan.com/online-tools/hmac-sha256-online?ref=blog.tericcabrel.com 

	public boolean validateToken(String token, UserDetails userDetails) {
		byte[] secretKeyBytes = jwtSecret.getBytes();
		SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);
		JwtParser parser = Jwts.parser().verifyWith(key).build();
		Claims claims = parser.parseSignedClaims(token).getPayload();
		String extractedUsername = (String) claims.getSubject();
		return (extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		byte[] secretKeyBytes = jwtSecret.getBytes();
		SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);
		JwtParser parser = Jwts.parser().verifyWith(key).build();
		Claims claims = parser.parseSignedClaims(token).getPayload();
		return claims.getExpiration();
	}

	public String generateToken(String username) {
		byte[] secretKeyBytes = jwtSecret.getBytes();
		SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
		Instant now = Instant.now();
		Instant expiration = now.plusSeconds(3600);
		Date expDate = Date.from(expiration);
		String token = Jwts.builder().subject(username).expiration(expDate).issuedAt(Date.from(now)).signWith(secretKey)
				.compact();
	    System.out.println("JWT Token: " + token);
		return token;
	}

	public String extractUsername(String token) {
		byte[] secretKeyBytes = jwtSecret.getBytes();
		SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);
		JwtParser parser = Jwts.parser().verifyWith(key).build();
		Claims claims = parser.parseSignedClaims(token).getPayload();
		String extractedUsername = claims.getSubject();
		return extractedUsername;

	}

}
