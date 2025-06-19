package org.nutrition.app.security.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import org.nutrition.app.user.dto.UserDTO;

@Builder(setterPrefix = "with")
@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthResponse(String accessToken, UserDTO user) {
}
