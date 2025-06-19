package org.nutrition.app.food.dto;

import java.io.Serializable;
import java.util.UUID;

public record NutritionMineralsDTO (
        UUID id,
        float portionSize,
        String unit,
        Double calcium,
        Double iron,
        Double magnesium,
        Double phosphorus,
        Double potassium,
        Double sodium,
        Double zinc,
        Double copper,
        Double manganese
) implements Serializable {
}
