package org.nutrition.app.security.repository;

import org.nutrition.app.security.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, UUID> {

    Optional<PasswordToken> findByToken(final String token);
}

