package org.nutrition.app.user.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.util.Role;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class CreateUserRequest {

        @NotBlank(message = NutritionError.USERNAME_REQUIRED)
        @Size(min = 3, max = 50, message = NutritionError.USERNAME_LENGTH)
        private String username;

        @NotBlank(message = NutritionError.PASSWORD_REQUIRED)
        @Size(min = 6, message = NutritionError.PASSWORD_TOO_SHORT)
        private String password;

        @NotBlank(message = NutritionError.EMAIL_REQUIRED)
        @Email(message = NutritionError.EMAIL_INVALID)
        private String email;

        @NotNull(message = NutritionError.ROLE_REQUIRED)
        private Role role;
}