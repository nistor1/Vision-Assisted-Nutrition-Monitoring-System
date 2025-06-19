package org.nutrition.app.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${app-mail}")
    private String fromEmail;

    @Value("${reset-password-url}")
    private String passwordResetUrl;

    @Value("${reset-password-email-title}")
    private String passwordResetTitle;

    @Value("${reset-password-email-body}")
    private String passwordResetBody;

    private final JavaMailSender mailSender;

    public void sendPasswordResetEmail(final String email, final String resetToken) {
        final String resetUrl = passwordResetUrl + "?token=" + resetToken;
        final String body = passwordResetBody + resetUrl;

        sendEmail(email, passwordResetTitle, body);
    }

    public void sendEmail(final String to, final String subject, final String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(fromEmail);
        mailSender.send(message);
    }
}


