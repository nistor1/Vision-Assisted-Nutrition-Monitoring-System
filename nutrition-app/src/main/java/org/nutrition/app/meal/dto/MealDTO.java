package org.nutrition.app.meal.dto;

import org.nutrition.app.meal.constants.MealStatus;
import org.nutrition.app.meal.constants.MealType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record MealDTO(
        UUID id,
        String name,
        MealType mealType,
        MealStatus mealStatus,
        Date createdAt,
        BigDecimal totalCalories,
        BigDecimal totalProteins,
        BigDecimal totalCarbohydrates,
        List<MealEntryDTO> entries
) implements Serializable {
}

