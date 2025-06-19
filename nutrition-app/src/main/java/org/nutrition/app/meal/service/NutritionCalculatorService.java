package org.nutrition.app.meal.service;


import org.nutrition.app.food.entity.FoodItem;
import org.nutrition.app.meal.entity.MealEntry;
import org.nutrition.app.meal.entity.NutritionTotals;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class NutritionCalculatorService {

    public NutritionTotals calculateTotals(List<MealEntry> entries) {
        NutritionTotals totals = new NutritionTotals();

        for (MealEntry entry : entries) {
            FoodItem food = entry.getFoodItem();
            BigDecimal quantity = BigDecimal.valueOf(entry.getQuantity());

            totals.setCalories(totals.getCalories()
                    .add(BigDecimal.valueOf(food.getProximates().getEnergyGeneral())
                            .multiply(BigDecimal.valueOf(food.getServingSize()))
                            .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                            .multiply(quantity)
                    ));

            totals.setCarbohydrates(totals.getCarbohydrates()
                    .add(BigDecimal.valueOf(food.getCarbohydrates().getCarbohydrate())
                            .multiply(BigDecimal.valueOf(food.getServingSize()))
                            .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                            .multiply(quantity)
                    ));

            totals.setProteins(totals.getProteins()
                    .add(BigDecimal.valueOf(food.getProximates().getProtein())
                            .multiply(BigDecimal.valueOf(food.getServingSize()))
                            .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                            .multiply(quantity)
                    ));
        }

        return totals;
    }
}

