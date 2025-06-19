package org.nutrition.app.security.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResetPasswordRequest(
        @JsonProperty String token,
        @JsonProperty String newPassword) {
}
