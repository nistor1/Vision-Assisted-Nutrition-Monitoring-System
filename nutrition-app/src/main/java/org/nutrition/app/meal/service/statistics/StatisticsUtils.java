package org.nutrition.app.meal.service.statistics;


import org.nutrition.app.food.entity.FoodItem;
import org.nutrition.app.food.entity.NutritionCarbohydrates;
import org.nutrition.app.food.entity.NutritionProximates;
import org.nutrition.app.meal.entity.MealEntry;
import org.nutrition.app.meal.entity.NutritionTotals;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class StatisticsUtils {

    public NutritionTotals calculateTotals(List<MealEntry> entries) {
        NutritionTotals totals = new NutritionTotals();

        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProteins = BigDecimal.ZERO;
        BigDecimal totalFats = BigDecimal.ZERO;
        BigDecimal totalCarbs = BigDecimal.ZERO;
        BigDecimal totalSugars = BigDecimal.ZERO;

        for (MealEntry entry : entries) {
            FoodItem food = entry.getFoodItem();
            BigDecimal quantity = BigDecimal.valueOf(entry.getQuantity());
            BigDecimal servingSize = BigDecimal.valueOf(food.getServingSize());
            BigDecimal gramsConsumed = quantity.multiply(servingSize);

            totalCalories = getTotalCalories(totalCalories, food, gramsConsumed);
            totalProteins = getTotalProteins(totalProteins, food, gramsConsumed);
            totalFats = getTotalFats(totalFats, food, gramsConsumed);
            totalCarbs = getTotalCarbohydrates(totalCarbs, food, gramsConsumed);
            totalSugars = getTotalSugars(totalSugars, food, gramsConsumed);
        }
        totals.setCalories(totalCalories);
        totals.setProteins(totalProteins);
        totals.setCarbohydrates(totalCarbs);
        totals.setFats(totalFats);
        totals.setSugars(totalSugars);

        return totals;
    }

    public BigDecimal getTotalCalories(BigDecimal currentTotal, FoodItem food, BigDecimal gramsConsumed) {
        return currentTotal.add(scale(
                get(food.getProximates(), NutritionProximates::getEnergySpecific),
                gramsConsumed,
                BigDecimal.valueOf(food.getProximates().getPortionSize()))
        );
    }

    public BigDecimal getTotalProteins(BigDecimal currentTotal, FoodItem food, BigDecimal gramsConsumed) {
        return currentTotal.add(scale(
                get(food.getProximates(), NutritionProximates::getProtein),
                gramsConsumed,
                BigDecimal.valueOf(food.getProximates().getPortionSize()))
        );
    }

    public BigDecimal getTotalFats(BigDecimal currentTotal, FoodItem food, BigDecimal gramsConsumed) {
        return currentTotal.add(scale(
                get(food.getProximates(), NutritionProximates::getTotalLipid),
                gramsConsumed,
                BigDecimal.valueOf(food.getProximates().getPortionSize()))
        );
    }

    public BigDecimal getTotalCarbohydrates(BigDecimal currentTotal, FoodItem food, BigDecimal gramsConsumed) {
        return currentTotal.add(scale(
                get(food.getCarbohydrates(), NutritionCarbohydrates::getCarbohydrate),
                gramsConsumed,
                BigDecimal.valueOf(food.getCarbohydrates().getPortionSize()))
        );
    }

    public BigDecimal getTotalSugars(BigDecimal currentTotal, FoodItem food, BigDecimal gramsConsumed) {
        return currentTotal.add(scale(
                get(food.getCarbohydrates(), NutritionCarbohydrates::getTotalSugars),
                gramsConsumed,
                BigDecimal.valueOf(food.getCarbohydrates().getPortionSize()))
        );
    }

    public BigDecimal scale(BigDecimal valuePer100g, BigDecimal gramsConsumed, BigDecimal gramsReference) {
        return valuePer100g
                .multiply(gramsConsumed)
                .divide(gramsReference, MathContext.DECIMAL64);
    }

    public <T> BigDecimal get(T object, Function<T, Double> extractor) {
        if (object == null) return BigDecimal.ZERO;
        Double value = extractor.apply(object);
        return value != null ? BigDecimal.valueOf(value) : BigDecimal.ZERO;
    }

    public <T, U> T addIfPresent(T current, U source, BiFunction<T, U, T> adder) {
        return source != null ? adder.apply(current, source) : current;
    }

}

