package org.nutrition.app.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.nutrition.app.util.Role;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserDTO(
        UUID id,
        String username,
        String email,
        Timestamp createdAt,
        Role role,
        String fullName,
        String address,
        String city,
        String country,
        String postalCode,
        String phoneNumber
) implements Serializable {
}

