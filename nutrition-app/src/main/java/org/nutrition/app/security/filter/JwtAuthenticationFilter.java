package org.nutrition.app.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nutrition.app.security.config.AppContext;
import org.nutrition.app.user.entity.User;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.security.service.JwtService;
import org.nutrition.app.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.PathContainer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.pattern.PathPatternParser;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${whitelisted-paths}")
    private List<String> whitelistedPaths;

    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final UserService usersService;
    private final PathPatternParser pathPatternParser;
    private final AppContext appContext;

    @SneakyThrows
    @Override
    protected void doFilterInternal(
            final @NonNull HttpServletRequest request,
            final @NonNull HttpServletResponse response,
            final @NonNull FilterChain filterChain) {

        log.info("[AuthFilter] {} {}", request.getMethod(), request.getRequestURI());
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!isValidHeader(authHeader)) {
            handleInvalidAuth(response, NutritionError.BAD_TOKEN, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String jwtToken = getJwtFromHeader(authHeader);
        appContext.setToken(jwtToken);
        Optional<String> usernameOptional = jwtService.extractUsername(jwtToken);
        if (usernameOptional.isEmpty()) {
            handleInvalidAuth(response, NutritionError.BAD_TOKEN, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String username = usernameOptional.get();
        try {
            User user = usersService.loadUserByUsername(username);
            if (!isAuthenticated(username) && jwtService.isValidToken(jwtToken, user.getId(), user.getUsername(), user.getRole())) {
                authenticate(user, request);
            } else {
                handleInvalidAuth(response, NutritionError.BAD_TOKEN, HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (Exception e) {
            log.error("[AuthFilter] {}", e.getMessage());
            handleInvalidAuth(response, new NutritionError(e.getMessage()), HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    protected void authenticate(final UserDetails details, final HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                details,
                getJwtFromHeader(request.getHeader(HttpHeaders.AUTHORIZATION)),
                details.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private void handleInvalidAuth(@NonNull HttpServletResponse response, final NutritionError error, final int status) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(status);
            objectMapper.writeValue(response.getWriter(), error);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write error response", e);
        }
    }

    private boolean isValidHeader(final String header) {
        return StringUtils.isNotBlank(header) && header.startsWith("Bearer ");
    }

    private String getJwtFromHeader(final String header) {
        return header.substring(7);
    }

    private boolean isAuthenticated(final String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(auth) && auth.getName().equals(username);
    }

    @Override
    protected boolean shouldNotFilter(@NotNull final HttpServletRequest request) {
        return whitelistedPaths.stream()
                .map(pathPatternParser::parse)
                .anyMatch(pattern -> pattern.matches(PathContainer.parsePath(request.getRequestURI())));
    }
}
