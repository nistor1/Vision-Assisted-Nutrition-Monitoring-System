package org.nutrition.app.meal.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nutrition.app.food.entity.FoodItem;
import org.nutrition.app.meal.dto.response.DailyStatisticResponse;
import org.nutrition.app.meal.entity.Meal;
import org.nutrition.app.meal.entity.MealEntry;
import org.nutrition.app.meal.repository.MealRepository;
import org.nutrition.app.meal.service.statistics.DailyStatisticsService;
import org.nutrition.app.meal.service.statistics.StatisticsUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.nutrition.app.util.TestUtils.FoodUtils.randomFoodItem;


@ExtendWith(MockitoExtension.class)
class DailyStatisticsServiceTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private StatisticsUtils statisticsUtils;

    @InjectMocks
    private DailyStatisticsService dailyStatisticsService;


    @Test
    void testGetStatisticsForDay_shouldReturnAggregatedResponse() {
        UUID userId = UUID.randomUUID();
        LocalDate date = LocalDate.of(2025, 7, 4);

        FoodItem foodItem = randomFoodItem();
        foodItem.setServingSize(1.0);

        MealEntry entry = MealEntry.builder()
                .withFoodItem(foodItem)
                .withQuantity(100.0)
                .build();

        Meal meal = Meal.builder()
                .withUserId(userId)
                .withCreatedAt(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withEntries(List.of(entry))
                .build();

        List<Meal> meals = List.of(meal);

        when(mealRepository.findAllByUserIdAndCreatedAtBetween(any(), any(), any()))
                .thenReturn(meals);

        when(statisticsUtils.getTotalCalories(any(), any(), any())).thenReturn(new BigDecimal("100"));
        when(statisticsUtils.getTotalProteins(any(), any(), any())).thenReturn(new BigDecimal("10"));
        when(statisticsUtils.getTotalFats(any(), any(), any())).thenReturn(new BigDecimal("5"));
        when(statisticsUtils.getTotalCarbohydrates(any(), any(), any())).thenReturn(new BigDecimal("20"));
        when(statisticsUtils.getTotalSugars(any(), any(), any())).thenReturn(new BigDecimal("6"));

        when(statisticsUtils.addIfPresent(any(), any(), any())).thenAnswer(inv -> inv.getArgument(0));

        Optional<DailyStatisticResponse> result = dailyStatisticsService.getStatisticsForDay(userId, date);

        assertTrue(result.isPresent());
        DailyStatisticResponse response = result.get();

        assertEquals(date, response.date());
        assertEquals(new BigDecimal("100"), response.totalCalories());
        assertEquals(new BigDecimal("10"), response.totalProteins());
        assertEquals(new BigDecimal("5"), response.totalFats());
        assertEquals(new BigDecimal("20"), response.totalCarbs());
        assertEquals(new BigDecimal("6"), response.totalSugars());

        verify(mealRepository).findAllByUserIdAndCreatedAtBetween(eq(userId), any(), any());
        verify(statisticsUtils).getTotalCalories(any(), any(), any());
    }

}
