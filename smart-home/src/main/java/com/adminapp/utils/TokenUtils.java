package com.adminapp.utils;

import com.adminapp.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenUtils {
    private final SecureRandom secureRandom = new SecureRandom();
    @Value("app-security")
    private String APP_NAME;

    @Value("1800000") // 30 min
    private int EXPIRES_IN;
    @Value("secret")
    private String SECRET;

    @Value("Authorization")
    private String AUTH_HEADER;

    @Value("Cookie")
    private String COOKIE_HEADER;

    // type of device for jwt token creation
    private static final String AUDIENCE_WEB = "web";

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(String id, Collection<GrantedAuthority> authorities, String fingerprint) {
        String fingerprintHash = generateFingerprintHash(fingerprint);

        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(id)
                .claim("authority", authorities)
                .setAudience(AUDIENCE_WEB)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .claim("userFingerprint", fingerprintHash)
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + EXPIRES_IN);
    }

    public String getToken(HttpServletRequest req) {
        String authHeader = req.getHeader(AUTH_HEADER);

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getRoleFromToken(String token) {
        String role;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            ArrayList<HashMap<String, String>> authorities = (ArrayList<HashMap<String, String>>) claims.get("authority");
            role = authorities.get(0).getOrDefault("authority", null);
        } catch (Exception e) {
            role = null;
        }
        return role;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        
        return claims;
    }
    
    public Boolean validateToken(String token, UserDetails userDetails, String fingerprint) {
        String identity = getIdentityFromToken(token);

        final String username = getUsernameFromToken(token);

        boolean isUsernameValid = username != null // korisnicko ime nije null
                && username.equals(userDetails.getUsername());

        // Validiranje fingerprint-a
        System.out.println("FGP ===> " + fingerprint);
        boolean isAlgorithmValid = true;
        if (fingerprint != null) {
            isAlgorithmValid = SIGNATURE_ALGORITHM.getValue().equals(getAlgorithmFromToken(token));
        }
        return isUsernameValid && isAlgorithmValid;

//        return (identity != null);
    }

    private String getAlgorithmFromToken(String token) {
        String algorithm;
        try {
            algorithm = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getHeader()
                    .getAlgorithm();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            algorithm = null;
        }
        return algorithm;
    }

    private boolean validateTokenFingerprint(String fingerprint, String token) {
        // Hesiranje fingerprint-a radi poređenja sa hesiranim fingerprint-om u tokenu
        String fingerprintHash = generateFingerprintHash(fingerprint);
        String fingerprintFromToken = getFingerprintFromToken(token);
        return fingerprintFromToken.equals(fingerprintHash);
    }

    private String getFingerprintFromToken(String token) {
        String fingerprint;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            fingerprint = claims.get("userFingerprint", String.class);
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            fingerprint = null;
        }
        return fingerprint;
    }

    private String generateFingerprintHash(String userFingerprint) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] userFingerprintDigest = digest.digest(userFingerprint.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(userFingerprintDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getUsernameFromToken(String token) {
        String username;

        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            username = null;
        }

        return username;
    }

    public String getIdentityFromToken(String token) {
        String identity;

        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            identity = claims.getSubject().split(",")[0];
        } catch (Exception e) {
            identity = null;
        }
        return identity;
    }

    public String generateFingerprint() {
        // Generisanje random string-a koji ce predstavljati fingerprint za korisnika
        byte[] randomFgp = new byte[50];
        this.secureRandom.nextBytes(randomFgp);
        return DatatypeConverter.printHexBinary(randomFgp);
    }

    public String getFingerprintFromCookie(HttpServletRequest request) {
        String userFingerprint = null;
        if (request.getCookies() != null && request.getCookies().length > 0) {
            List<Cookie> cookies = Arrays.stream(request.getCookies()).collect(Collectors.toList());
            Optional<Cookie> cookie = cookies.stream().filter(c -> "Fingerprint".equals(c.getName())).findFirst();

            if (cookie.isPresent()) {
                userFingerprint = cookie.get().getValue();
            }
        }
        return userFingerprint;
    }

}
