package org.nutrition.app.food.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public record FoodItemDTO(
        UUID id,
        Integer tag,
        String foodName,
        String category,
        String imageUrl,
        Double servingSize,
        String servingUnit,
        Date createdAt,
        Date updatedAt,
        boolean active,
        NutritionProximatesDTO proximates,
        NutritionMineralsDTO minerals,
        NutritionCarbohydratesDTO carbohydrates,
        NutritionVitaminsDTO vitamins
) implements Serializable {
}
