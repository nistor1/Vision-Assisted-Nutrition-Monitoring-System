package org.nutrition.app.exception;

import jakarta.validation.ConstraintViolationException;
import org.nutrition.app.util.response.NutritionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public NutritionResponse<?> handleValidationException(MethodArgumentNotValidException ex) {
        List<NutritionError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> new NutritionError("Invalid field: " + field.getField() + " - " + field.getDefaultMessage()))
                .toList();

        return NutritionResponse.failureResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public NutritionResponse<?> handleUnreadableBody(HttpMessageNotReadableException ex) {
        return NutritionResponse.failureResponse(
                new NutritionError("Malformed request body"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public NutritionResponse<?> handleConstraintViolation(ConstraintViolationException ex) {
        return NutritionResponse.failureResponse(
                new NutritionError("Constraint violation: " + ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NutritionException.class)
    @ResponseBody
    public NutritionResponse<?> handleNutritionException(final NutritionException exception) {
        return NutritionResponse.failureResponse(
                        new NutritionError(exception.getMessage()),
                        exception.getStatus()
                );
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public NutritionResponse<?> handleGenericException(final Exception exception) {
        return NutritionResponse.failureResponse(
                        new NutritionError("Unexpected error: " + exception.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
    }

}
