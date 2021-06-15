package backend.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import backend.models.User;
import backend.services.impl.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {

	@Value("spring-security-example")
	private String APP_NAME;

	// Tajna koju samo backend aplikacija treba da zna kako bi mogla da generise i proveri JWT https://jwt.io/
	@Value("somesecret")
	public String SECRET;

	// Period vazenja tokena - 30 minuta
	@Value("1800000")
	private int EXPIRES_IN;
	
	// Naziv headera kroz koji ce se prosledjivati JWT u komunikaciji server-klijent
	@Value("Authorization")
	private String AUTH_HEADER;
	
	private static final String AUDIENCE_WEB = "web";
	
	@Autowired
	private UserService userService;

	// Algoritam za potpisivanje JWT
	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
	
	public String generateToken(String username) {
		return Jwts.builder()
				.setIssuer(APP_NAME)
				.setSubject(username)
				.setAudience(generateAudience())
				.setIssuedAt(new Date())
				.setExpiration(generateExpirationDate())
				.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
	}
		
	private String generateAudience() {
		
		//	Moze se iskoristiti org.springframework.mobile.device.Device objekat za odredjivanje tipa uredjaja sa kojeg je zahtev stigao.
		//	https://spring.io/projects/spring-mobile
				
		//	String audience = AUDIENCE_UNKNOWN;
		//		if (device.isNormal()) {
		//			audience = AUDIENCE_WEB;
		//		} else if (device.isTablet()) {
		//			audience = AUDIENCE_TABLET;
		//		} else if (device.isMobile()) {
		//			audience = AUDIENCE_MOBILE;
		//		}
		
		return AUDIENCE_WEB;
	}
	
	private Date generateExpirationDate() {
		return new Date(new Date().getTime() + EXPIRES_IN);
	}
	
	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);

		// JWT se prosledjuje kroz header 'Authorization' u formatu:
		// Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7); // preuzimamo samo token (vrednost tokena je nakon "Bearer " prefiksa)
		}

		return null;
	}
	
	public String getUsernameFromToken(String token) {
		String username;
		
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
		} catch (ExpiredJwtException ex) {
			username = null;
		} catch (Exception e) {
			username = null;
		}
		
		return username;
	}
	
	public Date getIssuedAtDateFromToken(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getIssuedAt();
		} catch (ExpiredJwtException ex) {
			System.out.println("Token expired");
			issueAt = null;
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}
	
	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			audience = claims.getAudience();
		} catch (ExpiredJwtException ex) {
			System.out.println("Token expired");
			audience = null;
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}
	
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (ExpiredJwtException ex) {
			System.out.println("Token expired");
			expiration = null;
		} catch (Exception e) {
			expiration = null;
		}
		
		return expiration;
	}
	
	/**
	 * Funkcija za čitanje svih podataka iz JWT tokena
	 * 
	 * @param token JWT token.
	 * @return Podaci iz tokena.
	 */
	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException ex) {
			System.out.println("Token expired");
			claims = null;
		} catch (Exception e) {
			claims = null;
		}
		
		// Preuzimanje proizvoljnih podataka je moguce pozivom funkcije claims.get(key)
		
		return claims;
	}
	
	public Boolean validateToken(String token, User userDetails) {
		User user = userDetails;
		final String username = getUsernameFromToken(token);
		//final Date created = getIssuedAtDateFromToken(token);
		
		// Token je validan kada:
		return (username != null // korisnicko ime nije null
			&& username.equals(user.getEmail())); // korisnicko ime iz tokena se podudara sa korisnickom imenom koje pise u bazi); 
	}
	
	public int getExpiredIn() {
		return EXPIRES_IN;
	}
	
	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER);
	}

	public boolean validate(String token) {
		String uname = getUsernameFromToken(token);
		return (uname != null && userService.findUserByEmail(uname) != null);
	}
	
}
