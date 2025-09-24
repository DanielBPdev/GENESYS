package com.asopagos.usuarios.ejb;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import com.asopagos.log.ILogger;
import com.asopagos.log.LogManager;

public class PasswordResetTokenService {

    private final ILogger logger = LogManager.getLogger(PasswordResetTokenService.class);

    private final String jwtSecret;
    private final long jwtExpirationMs;
    private final SecretKey signingKey;

    /**
     * Constructor para inicializar el servicio con el secreto y tiempo de expiración.
     * Estos valores deben ser cargados de forma segura (ej. BD, variable de entorno).
     * @param jwtSecret La clave secreta para firmar los JWT.
     * @param jwtExpirationMs La duración de validez de los tokens en milisegundos.
     */
    public PasswordResetTokenService(String jwtSecret, long jwtExpirationMs) {
        if (jwtSecret == null || jwtSecret.isEmpty()) {
            throw new IllegalArgumentException("JWT Secret no puede ser nulo o vacío.");
        }
        if (jwtExpirationMs <= 0) {
            throw new IllegalArgumentException("JWT Expiration debe ser un valor positivo.");
        }

        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    private SecretKey getSigningKey() {
        return this.signingKey;
    }

    public String generatePasswordResetToken(String userId, String email) {
        Date now = new Date();
        logger.info("PasswordResetTokenService - Hora actual del sistema (now): " + now);
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        logger.info("PasswordResetTokenService - Hora de expiración calculada (expiryDate): " + expiryDate);
        String jti = UUID.randomUUID().toString();

        String token = Jwts.builder()
                .setId(jti)
                .setSubject(userId)
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public String validatePasswordResetToken(String token, String email) {
        try {
            io.jsonwebtoken.Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String tokenEmail = claims.get("email", String.class);
            String userId = claims.getSubject();

            if (!email.equals(tokenEmail)) {
                // Logear esto si el email no coincide con el del token.
                return null;
            }

            return userId;
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // Token expirado
            return null;
        } catch (io.jsonwebtoken.security.SignatureException | io.jsonwebtoken.MalformedJwtException e) {
            // Firma inválida o token malformado
            return null;
        } catch (Exception e) {
            // Otros errores
            return null;
        }
    }
}