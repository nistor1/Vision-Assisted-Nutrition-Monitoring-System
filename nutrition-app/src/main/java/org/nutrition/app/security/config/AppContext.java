package org.nutrition.app.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Component
@RequestScope
@Getter
@Setter
public class AppContext {

    private UUID userId;

    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public void setToken(final String tokenToBeSet) {
        TOKEN.set(tokenToBeSet);
    }

    public UUID getUserId() {
        return userId;
    }

    public String getToken() {
        return TOKEN.get();
    }

    public void clear() {
        TOKEN.remove();
    }
}
