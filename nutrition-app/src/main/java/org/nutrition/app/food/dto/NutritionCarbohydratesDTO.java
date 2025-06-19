package org.nutrition.app.food.dto;

import java.io.Serializable;
import java.util.UUID;

public record NutritionCarbohydratesDTO(
        UUID id,
        Double portionSize,
        String unit,
        Double carbohydrate,
        Double fiber,
        Double totalSugars,
        Double sucrose,
        Double glucose,
        Double fructose,
        Double maltose,
        Double lactose
) implements Serializable {
}
