package org.nutrition.app.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.nutrition.app.user.dto.UserDTO;
import org.nutrition.app.user.entity.User;
import org.nutrition.app.util.Constants.Time;
import org.nutrition.app.util.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    @Value("${spring.jwt.token-expiration-days}")
    private Integer tokenExpirationDays;

    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isValidToken(final String token, final UUID userId, final String username, final Role role) {
        return !isExpiredToken(token)
                && hasValidUserId(token, userId)
                && hasValidUsername(token, username)
                && hasValidRole(token, role);
    }

    public String generateToken(final UserDTO userDTO) {
        return generateToken(userDTO.id(), userDTO.username(), userDTO.role());
    }

    public String generateToken(final User user) {
        return generateToken(user.getId(), user.getUsername(), user.getRole());
    }

    public String generateToken(final UUID id, final String username, final Role role) {
        return Jwts.builder()
                .claim("id", id.toString())
                .claim("role", role.name())
                .setSubject(username)
                .setIssuedAt(Time.now())
                .setExpiration(Time.nowWithDelay(Time.DAY * tokenExpirationDays))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Optional<Claims> extractAllClaims(final String token) {
        try {
            return Optional.of(
                    Jwts.parserBuilder()
                            .setSigningKey(getSigningKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private <T> Optional<T> extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        return extractAllClaims(token).map(claimsResolver);
    }

    public Optional<UUID> extractUserId(final String token) {
        return extractClaim(token, claims -> claims.get("id", String.class)).map(UUID::fromString);
    }

    public Optional<String> extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Optional<Role> extractRole(final String token) {
        return extractClaim(token, claims -> claims.get("role", String.class)).map(Role::valueOf);
    }

    private Optional<Date> extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isExpiredToken(final String token) {
        return extractExpiration(token)
                .map(date -> date.before(Time.now()))
                .orElse(false);
    }

    private boolean hasValidUserId(final String token, final UUID userId) {
        return extractUserId(token)
                .map(e -> e.equals(userId))
                .orElse(false);
    }

    private boolean hasValidUsername(final String token, final String username) {
        return extractUsername(token)
                .map(e -> e.equals(username))
                .orElse(false);
    }

    private boolean hasValidRole(final String token, final Role role) {
        return extractRole(token)
                .map(e -> e.equals(role))
                .orElse(false);
    }
}
