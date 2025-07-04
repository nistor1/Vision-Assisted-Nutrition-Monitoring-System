package org.nutrition.app.security.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

import org.nutrition.app.security.dto.request.LoginRequest;
import org.nutrition.app.security.entity.PasswordToken;
import org.nutrition.app.security.repository.PasswordTokenRepository;
import org.nutrition.app.user.dto.UserDTO;
import org.nutrition.app.user.service.UserService;
import org.nutrition.app.util.Mapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.nutrition.app.util.TestUtils.UserUtils.createUserRequest;
import static org.nutrition.app.util.TestUtils.UserUtils.randomUser;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserService userService;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordTokenRepository passwordTokenRepository;

    @InjectMocks
    private AuthService authService;

    // Helper
    private final String email = "john.doe@example.com";
    private final String password = "password123";

    @Test
    void testAuthenticate_validCredentials_shouldReturnAuthResponse() {
        var loginRequest = new LoginRequest("john", password);
        var user = randomUser();
        var userDTO = Mapper.mapTo(user, UserDTO.class);
        var token = "mocked.jwt.token";

        when(userService.loadUserByUsername("john")).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(token);

        var result = authService.authenticate(loginRequest);

        assertTrue(result.isPresent());
        assertThat(result.get().accessToken(), equalTo(token));
        assertThat(result.get().user(), equalTo(userDTO));
    }

    @Test
    void testAuthenticate_userNotFound_shouldReturnEmpty() {
        var loginRequest = new LoginRequest("nonexistent", password);

        when(userService.loadUserByUsername("nonexistent")).thenThrow(new UsernameNotFoundException("User not found"));

        var result = authService.authenticate(loginRequest);

        assertTrue(result.isEmpty());
    }

    @Test
    void testRegister_validRequest_shouldReturnAuthResponse() {
        var user = randomUser();
        var userDTO = Mapper.mapTo(user, UserDTO.class);
        var createUserRequest = createUserRequest(user);
        var token = "registered.jwt.token";

        when(userService.create(createUserRequest)).thenReturn(Optional.of(userDTO));
        when(jwtService.generateToken(userDTO)).thenReturn(token);

        var result = authService.register(createUserRequest);

        assertTrue(result.isPresent());
        assertThat(result.get().accessToken(), equalTo(token));
        assertThat(result.get().user(), equalTo(userDTO));
    }

    @Test
    void testRegister_creationFails_shouldReturnEmpty() {
        var user = randomUser();
        var request = createUserRequest(user);
        when(userService.create(request)).thenReturn(Optional.empty());

        var result = authService.register(request);

        assertTrue(result.isEmpty());
    }

    @Test
    void testForgotPassword_validEmail_shouldSendResetEmailAndReturnTrue() {
        var userDTO = Mapper.mapTo(randomUser(), UserDTO.class);
        when(userService.findByEmail(email)).thenReturn(Optional.of(userDTO));

        var token = new PasswordToken(UUID.randomUUID(), UUID.randomUUID().toString(), userDTO.id(), LocalDateTime.now().plusMinutes(5));
        when(passwordTokenRepository.save(any(PasswordToken.class))).thenReturn(token);

        boolean result = authService.forgotPassword(email);

        verify(passwordTokenRepository).save(any(PasswordToken.class));
        verify(emailService).sendPasswordResetEmail(eq(email), anyString());
        assertTrue(result);
    }

    @Test
    void testForgotPassword_emailNotFound_shouldReturnFalse() {
        when(userService.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = authService.forgotPassword(email);

        assertFalse(result);
    }

    @Test
    void testResetPassword_validToken_shouldUpdatePasswordAndReturnTrue() {
        UUID userId = UUID.randomUUID();
        var token = "valid-token";
        var passwordToken = new PasswordToken(UUID.randomUUID(), token, userId, LocalDateTime.now().plusMinutes(5));

        when(passwordTokenRepository.findByToken(token)).thenReturn(Optional.of(passwordToken));
        when(userService.updateUserPassword(userId, "newPassword123")).thenReturn(true);

        boolean result = authService.resetPassword(token, "newPassword123");

        verify(passwordTokenRepository).delete(passwordToken);
        assertTrue(result);
    }

    @Test
    void testResetPassword_tokenNotFound_shouldReturnFalse() {
        when(passwordTokenRepository.findByToken("invalid-token")).thenReturn(Optional.empty());

        boolean result = authService.resetPassword("invalid-token", "irrelevant");

        assertFalse(result);
    }

    @Test
    void testResetPassword_passwordUpdateFails_shouldReturnFalse() {
        UUID userId = UUID.randomUUID();
        var token = "valid-token";
        var passwordToken = new PasswordToken(UUID.randomUUID(), token, userId, LocalDateTime.now().plusMinutes(5));

        when(passwordTokenRepository.findByToken(token)).thenReturn(Optional.of(passwordToken));
        when(userService.updateUserPassword(userId, "newPassword123")).thenReturn(false);

        boolean result = authService.resetPassword(token, "newPassword123");

        verify(passwordTokenRepository, never()).delete(passwordToken);
        assertFalse(result);
    }
}
