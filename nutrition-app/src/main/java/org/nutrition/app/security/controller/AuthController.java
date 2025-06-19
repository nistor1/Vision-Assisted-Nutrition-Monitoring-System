package org.nutrition.app.security.controller;

import lombok.RequiredArgsConstructor;
import org.nutrition.app.util.response.NutritionResponse;
import org.nutrition.app.security.dto.request.ForgotPasswordRequest;
import org.nutrition.app.security.dto.request.LoginRequest;
import org.nutrition.app.security.dto.request.ResetPasswordRequest;
import org.nutrition.app.security.dto.request.ValidateTokenRequest;
import org.nutrition.app.security.dto.response.AuthResponse;
import org.nutrition.app.security.service.AuthService;
import org.nutrition.app.user.dto.UserDTO;
import org.nutrition.app.user.dto.request.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.nutrition.app.exception.NutritionError.BAD_CREDENTIAL_EXCEPTION;
import static org.nutrition.app.exception.NutritionError.PASSWORD_RESET_FAIL;
import static org.nutrition.app.exception.NutritionError.REGISTRATION_FAILED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public NutritionResponse<AuthResponse> login(final @RequestBody LoginRequest loginRequest) {
        return authService.authenticate(loginRequest)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(BAD_CREDENTIAL_EXCEPTION, HttpStatus.UNAUTHORIZED));
    }

    @PostMapping("/register")
    public NutritionResponse<AuthResponse> register(@RequestBody final CreateUserRequest createUserRequest) {
        return authService.register(createUserRequest)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(REGISTRATION_FAILED, BAD_REQUEST));
    }

    @PostMapping("/forgot-password")
    public NutritionResponse<?> forgotPassword(@RequestBody final ForgotPasswordRequest request) {
        return authService.forgotPassword(request.email()) ?
                NutritionResponse.successResponse(null) :
                NutritionResponse.failureResponse(PASSWORD_RESET_FAIL, BAD_REQUEST);
    }

    @PostMapping("/reset-password")
    public NutritionResponse<?> resetPassword(@RequestBody final ResetPasswordRequest request) {
        return authService.resetPassword(request.token(), request.newPassword()) ?
                NutritionResponse.successResponse(null) :
                NutritionResponse.failureResponse(PASSWORD_RESET_FAIL, BAD_REQUEST);
    }

    @PostMapping("/validate-token")
    public NutritionResponse<UserDTO> validateToken(@RequestBody final ValidateTokenRequest request) {
        return authService.validateToken(request.token())
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(BAD_CREDENTIAL_EXCEPTION, HttpStatus.UNAUTHORIZED));
    }
}
