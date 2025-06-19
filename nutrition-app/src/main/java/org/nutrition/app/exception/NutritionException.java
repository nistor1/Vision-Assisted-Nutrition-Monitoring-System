package org.nutrition.app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NutritionException extends RuntimeException {
    private final HttpStatus status;

    public NutritionException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
    }

    public NutritionException(final String message, final HttpStatus status, final Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
