package org.nutrition.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.nutrition.app.exception.NutritionException;
import org.springframework.http.HttpStatus;

public class Mapper {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static <T, U> void updateValues(final T value, final U newValue) {
        try {
            objectMapper.updateValue(value, newValue);
        } catch (JsonMappingException e) {
            throw new NutritionException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static <T> T mapTo(final Object object, Class<T> classTo) {
        try {
            return objectMapper.convertValue(object, classTo);
        } catch (IllegalArgumentException e) {
            throw new NutritionException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static String writeValueAsString(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new NutritionException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static byte[] writeValueAsBytes(final Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new NutritionException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
