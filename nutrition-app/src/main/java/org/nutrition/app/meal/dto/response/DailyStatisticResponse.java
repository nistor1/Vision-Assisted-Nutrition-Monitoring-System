package org.nutrition.app.meal.dto.response;

import org.nutrition.app.food.dto.NutritionCarbohydratesDTO;
import org.nutrition.app.food.dto.NutritionMineralsDTO;
import org.nutrition.app.food.dto.NutritionProximatesDTO;
import org.nutrition.app.food.dto.NutritionVitaminsDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyStatisticResponse(
        LocalDate date,
        BigDecimal totalCalories,
        BigDecimal totalProteins,
        BigDecimal totalFats,
        BigDecimal totalSugars,
        BigDecimal totalCarbs,
        NutritionProximatesDTO proximates,
        NutritionMineralsDTO minerals,
        NutritionCarbohydratesDTO carbohydrates,
        NutritionVitaminsDTO vitamins
) implements Serializable {
}
