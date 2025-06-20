package org.nutrition.app.meal.dto;

import org.nutrition.app.food.dto.FoodItemSimpleDTO;

import java.io.Serializable;
import java.util.UUID;

public record MealEntryDTO(
        UUID id,
        FoodItemSimpleDTO foodItem,
        Double quantity
) implements Serializable {
}
