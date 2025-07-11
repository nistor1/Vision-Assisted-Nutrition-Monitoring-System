package org.nutrition.app.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.util.Role;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class UpdateUserRequest {

    @NotNull(message = NutritionError.ID_IS_REQUIRED)
    private UUID id;

    @Size(min = 3, max = 50, message = NutritionError.USERNAME_LENGTH)
    private String username;

    @Email(message = NutritionError.EMAIL_INVALID)
    private String email;

    private Role role;

    private String fullName;

    private String address;

    private String city;

    private String country;

    private String postalCode;

    private String phoneNumber;

}

