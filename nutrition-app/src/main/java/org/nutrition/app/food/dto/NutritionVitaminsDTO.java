package org.nutrition.app.food.dto;

import java.io.Serializable;
import java.util.UUID;

public record NutritionVitaminsDTO(
        UUID id,
        float portionSize,
        String unit,
        Double vitaminA,
        Double vitaminB1,
        Double vitaminB2,
        Double vitaminB3,
        Double vitaminB5,
        Double vitaminB6,
        Double vitaminB7,
        Double vitaminB9,
        Double vitaminB12,
        Double vitaminC,
        Double vitaminD,
        Double vitaminE,
        Double vitaminK
) implements Serializable {
}
