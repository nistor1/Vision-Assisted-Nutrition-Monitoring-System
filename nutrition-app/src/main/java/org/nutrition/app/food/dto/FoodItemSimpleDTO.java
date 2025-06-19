package org.nutrition.app.food.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FoodItemSimpleDTO(
        UUID id,
        String foodName,
        String category,
        Double servingSize,
        String servingUnit,
        boolean active
) implements Serializable {
}