package org.nutrition.app.meal.service.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrition.app.food.entity.FoodItem;
import org.nutrition.app.food.entity.NutritionCarbohydrates;
import org.nutrition.app.food.entity.NutritionMinerals;
import org.nutrition.app.food.entity.NutritionProximates;
import org.nutrition.app.food.entity.NutritionVitamins;
import org.nutrition.app.meal.dto.response.DailyStatisticResponse;
import org.nutrition.app.meal.entity.Meal;
import org.nutrition.app.meal.entity.MealEntry;
import org.nutrition.app.meal.repository.MealRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyStatisticsService {

    private final MealRepository mealRepository;

    private final StatisticsUtils statisticsUtil;

    @Cacheable(
            value = "dailyStatistics",
            key = "#userId.toString() + '_' + #date.toString()"
    )
    public Optional<DailyStatisticResponse> getStatisticsForDay(UUID userId, LocalDate date) {
        Date startOfDay = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Meal> meals = mealRepository.findAllByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);

        if (meals.isEmpty()) {
            return Optional.empty();
        }

        BigDecimal totalCalories = BigDecimal.ZERO;
        BigDecimal totalProteins = BigDecimal.ZERO;
        BigDecimal totalFats = BigDecimal.ZERO;
        BigDecimal totalCarbs = BigDecimal.ZERO;
        BigDecimal totalSugars = BigDecimal.ZERO;

        var proximates = NutritionDtoAggregator.emptyProximates();
        var carbohydrates = NutritionDtoAggregator.emptyCarbohydrates();
        var minerals = NutritionDtoAggregator.emptyMinerals();
        var vitamins = NutritionDtoAggregator.emptyVitamins();

        for (Meal meal : meals) {
            if (meal.getEntries() == null) continue;

            for (MealEntry mealEntry : meal.getEntries()) {
                FoodItem food = mealEntry.getFoodItem();
                if (food == null) continue;

                BigDecimal quantity = BigDecimal.valueOf(mealEntry.getQuantity());
                BigDecimal servingSize = BigDecimal.valueOf(food.getServingSize());
                BigDecimal gramsConsumed = quantity.multiply(servingSize);

                totalCalories = statisticsUtil.getTotalCalories(totalCalories, food, gramsConsumed);
                totalProteins = statisticsUtil.getTotalProteins(totalProteins, food, gramsConsumed);
                totalFats = statisticsUtil.getTotalFats(totalFats, food, gramsConsumed);
                totalCarbs = statisticsUtil.getTotalCarbohydrates(totalCarbs, food, gramsConsumed);
                totalSugars = statisticsUtil.getTotalSugars(totalSugars, food, gramsConsumed);

                NutritionProximates scaledProximates = NutritionDtoAggregator.scale(food.getProximates(), gramsConsumed, BigDecimal.valueOf(food.getProximates().getPortionSize()));
                NutritionCarbohydrates scaledCarbohydrates = NutritionDtoAggregator.scale(food.getCarbohydrates(), gramsConsumed, BigDecimal.valueOf(food.getCarbohydrates().getPortionSize()));
                NutritionVitamins scaledVitamins = NutritionDtoAggregator.scale(food.getVitamins(), gramsConsumed, BigDecimal.valueOf(food.getVitamins().getPortionSize()));
                NutritionMinerals scaledMinerals = NutritionDtoAggregator.scale(food.getMinerals(), gramsConsumed, BigDecimal.valueOf(food.getMinerals().getPortionSize()));

                proximates = statisticsUtil.addIfPresent(proximates, scaledProximates, NutritionDtoAggregator::add);
                carbohydrates = statisticsUtil.addIfPresent(carbohydrates, scaledCarbohydrates, NutritionDtoAggregator::add);
                minerals = statisticsUtil.addIfPresent(minerals, scaledMinerals, NutritionDtoAggregator::add);
                vitamins = statisticsUtil.addIfPresent(vitamins, scaledVitamins, NutritionDtoAggregator::add);
            }
        }

        log.info("[Stats] Finished calculating statistics. Calories={}, Proteins={}, Fats={}, Sugars={}, Carbs={}",
                totalCalories, totalProteins, totalFats, totalSugars, totalCarbs);

        DailyStatisticResponse response = new DailyStatisticResponse(
                date,
                totalCalories,
                totalProteins,
                totalFats,
                totalSugars,
                totalCarbs,
                proximates,
                minerals,
                carbohydrates,
                vitamins
        );

        log.debug("[Stats] Returning DailyStatisticResponse: {}", response);
        return Optional.of(response);
    }
}
