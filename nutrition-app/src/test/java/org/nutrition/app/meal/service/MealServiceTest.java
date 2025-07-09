package org.nutrition.app.meal.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nutrition.app.food.entity.FoodItem;
import org.nutrition.app.food.repository.FoodItemRepository;
import org.nutrition.app.meal.constants.MealStatus;
import org.nutrition.app.meal.constants.MealType;
import org.nutrition.app.meal.dto.MealDTO;
import org.nutrition.app.meal.dto.request.CreateMealEntryRequest;
import org.nutrition.app.meal.dto.request.CreateMealRequest;
import org.nutrition.app.meal.dto.request.UpdateMealEntryRequest;
import org.nutrition.app.meal.dto.request.UpdateMealRequest;
import org.nutrition.app.meal.entity.DetectedObject;
import org.nutrition.app.meal.entity.Meal;
import org.nutrition.app.meal.entity.MealEntry;
import org.nutrition.app.meal.entity.NutritionTotals;
import org.nutrition.app.meal.repository.MealRepository;
import org.nutrition.app.meal.service.statistics.StatisticsUtils;
import org.nutrition.app.security.config.AppContext;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.nutrition.app.util.TestUtils.FoodUtils.randomFoodItem;
import static org.nutrition.app.util.TestUtils.MealUtils.randomMeal;


@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @Mock
    private InferenceClient inferenceClient;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private FoodItemRepository foodItemRepository;

    @Mock
    private Cache cache;

    @Mock
    private StatisticsUtils nutritionUtils;

    @Mock
    private AppContext appContext;


    @InjectMocks
    private MealService mealService;

    @Test
    void testFindById_shouldReturnDTO() {
        UUID id = UUID.randomUUID();
        Meal meal = Meal.builder()
                .withId(id)
                .withName("Test Meal")
                .withUserId(UUID.randomUUID())
                .withCreatedAt(new java.util.Date())
                .build();

        when(mealRepository.findById(id)).thenReturn(Optional.of(meal));

        var result = mealService.findById(id);

        verify(mealRepository).findById(id);
        assertTrue(result.isPresent());
        assertThat(result.get().getId(), equalTo(id));
    }

    @Test
    void testFindById_notFound_shouldReturnEmpty() {
        UUID id = UUID.randomUUID();
        when(mealRepository.findById(id)).thenReturn(Optional.empty());

        var result = mealService.findById(id);

        verify(mealRepository).findById(id);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindAll_withoutDate_shouldReturnMealsPage() {
        UUID userId = UUID.randomUUID();
        Pageable pageable = Pageable.unpaged();

        Meal meal = Meal.builder()
                .withId(UUID.randomUUID())
                .withUserId(userId)
                .withCreatedAt(new Date())
                .build();

        Page<Meal> page = new PageImpl<>(List.of(meal));
        when(mealRepository.findAllByUserId(userId, pageable)).thenReturn(page);

        var result = mealService.findAll(userId, null, pageable);

        verify(mealRepository).findAllByUserId(userId, pageable);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getTotalElements());
    }

    @Test
    void testFinalizeMeal_shouldUpdateStatusAndReturnDTO() {
        UUID mealId = UUID.randomUUID();
        Meal meal = Meal.builder()
                .withId(mealId)
                .withName("Dinner")
                .withUserId(UUID.randomUUID())
                .withCreatedAt(new Date())
                .withMealStatus(MealStatus.DRAFT)
                .build();

        when(mealRepository.findById(mealId)).thenReturn(Optional.of(meal));
        when(mealRepository.save(meal)).thenReturn(meal);

        var result = mealService.finalizeMeal(mealId);

        verify(mealRepository).save(meal);
        assertTrue(result.isPresent());
        assertEquals(MealStatus.FINALIZED, result.get().getMealStatus());
    }

    @Test
    void testDeleteById_shouldRemoveMealAndReturnDTO() {
        UUID mealId = UUID.randomUUID();
        Meal meal = Meal.builder()
                .withId(mealId)
                .withUserId(UUID.randomUUID())
                .withCreatedAt(new Date())
                .build();

        when(mealRepository.findById(mealId)).thenReturn(Optional.of(meal));
        when(cacheManager.getCache("dailyStatistics")).thenReturn(cache);

        var result = mealService.deleteById(mealId);

        verify(mealRepository).delete(meal);
        assertTrue(result.isPresent());
        assertEquals(mealId, result.get().getId());
    }

    @Test
    void testUpdate_shouldUpdateMealSuccessfully() {
        // Arrange
        Meal existingMeal = randomMeal();
        UUID mealId = existingMeal.getId();

        MealEntry oldEntry = existingMeal.getEntries().getFirst();
        UUID foodId1 = oldEntry.getFoodItem().getId();

        FoodItem newFood = randomFoodItem();
        UUID foodId2 = newFood.getId();

        UpdateMealRequest request = UpdateMealRequest.builder()
                .withId(mealId)
                .withName("Updated Name")
                .withMealType(MealType.DINNER)
                .withEntries(List.of(
                        UpdateMealEntryRequest.builder()
                                .withFoodItemId(foodId1)
                                .withQuantity(oldEntry.getQuantity() + 50)
                                .build(),
                        UpdateMealEntryRequest.builder()
                                .withFoodItemId(foodId2)
                                .withQuantity(120.0)
                                .build()
                ))
                .build();

        when(mealRepository.findById(mealId)).thenReturn(Optional.of(existingMeal));
        when(foodItemRepository.findById(foodId2)).thenReturn(Optional.of(newFood));
        NutritionTotals totals = new NutritionTotals();
        totals.setCalories(new BigDecimal("123.4"));
        totals.setProteins(new BigDecimal("10.2"));
        totals.setFats(new BigDecimal("5.1"));
        totals.setCarbohydrates(new BigDecimal("20.3"));
        totals.setSugars(new BigDecimal("6.6"));

        when(nutritionUtils.calculateTotals(any())).thenReturn(totals);
        when(mealRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(cacheManager.getCache("dailyStatistics")).thenReturn(cache);

        // Act
        Optional<MealDTO> result = mealService.update(request);

        // Assert
        assertTrue(result.isPresent());
        MealDTO updated = result.get();
        assertEquals("Updated Name", updated.getName());
        assertEquals(MealType.DINNER, updated.getMealType());
        assertEquals(2, updated.getEntries().size());

        verify(mealRepository).save(any());
        verify(nutritionUtils).calculateTotals(any());
        verify(cacheManager).getCache("dailyStatistics");
    }

    @Test
    void testCreateMeal_shouldCreateMealSuccessfully() {
        // Arrange
        UUID foodId1 = UUID.randomUUID();
        UUID foodId2 = UUID.randomUUID();

        FoodItem food1 = randomFoodItem();
        food1.setId(foodId1);
        FoodItem food2 = randomFoodItem();
        food2.setId(foodId2);

        CreateMealEntryRequest entry1 = CreateMealEntryRequest.builder()
                .withFoodItemId(foodId1)
                .withQuantity(100.0)
                .build();
        CreateMealEntryRequest entry2 = CreateMealEntryRequest.builder()
                .withFoodItemId(foodId2)
                .withQuantity(150.0)
                .build();

        CreateMealRequest request = CreateMealRequest.builder()
                .withName("My New Meal")
                .withMealType(MealType.BREAKFAST)
                .withEntries(List.of(entry1, entry2))
                .build();

        // Set up mock behaviors
        UUID userId = UUID.randomUUID();
        when(appContext.getUserId()).thenReturn(userId);
        when(foodItemRepository.findById(foodId1)).thenReturn(Optional.of(food1));
        when(foodItemRepository.findById(foodId2)).thenReturn(Optional.of(food2));

        NutritionTotals totals = new NutritionTotals();
        totals.setCalories(new BigDecimal("500"));
        totals.setProteins(new BigDecimal("30"));
        totals.setCarbohydrates(new BigDecimal("60"));
        totals.setFats(new BigDecimal("10"));
        totals.setSugars(new BigDecimal("15"));

        when(nutritionUtils.calculateTotals(any())).thenReturn(totals);
        when(mealRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(cacheManager.getCache("dailyStatistics")).thenReturn(cache);

        // Act
        Optional<MealDTO> result = mealService.createMeal(request);

        // Assert
        assertTrue(result.isPresent());
        MealDTO created = result.get();
        assertEquals("My New Meal", created.getName());
        assertEquals(MealType.BREAKFAST, created.getMealType());
        assertEquals(2, created.getEntries().size());

        verify(foodItemRepository).findById(foodId1);
        verify(foodItemRepository).findById(foodId2);
        verify(nutritionUtils).calculateTotals(any());
        verify(mealRepository).save(any());
        verify(cacheManager).getCache("dailyStatistics");
    }

    @Test
    void testCreateMealDraft_shouldCreateMealFromImageSuccessfully() {
        // Arrange
        MultipartFile mockImage = mock(MultipartFile.class);
        UUID userId = UUID.randomUUID();

        DetectedObject detected1 = new DetectedObject(101, 2);
        DetectedObject detected2 = new DetectedObject(202, 1);

        when(inferenceClient.predict(mockImage)).thenReturn(List.of(detected1, detected2));

        FoodItem food1 = randomFoodItem();
        food1.setTag(101);
        FoodItem food2 = randomFoodItem();
        food2.setTag(202);

        when(foodItemRepository.findByTag(101)).thenReturn(Optional.of(food1));
        when(foodItemRepository.findByTag(202)).thenReturn(Optional.of(food2));
        when(appContext.getUserId()).thenReturn(userId);

        // Act
        Optional<CreateMealRequest> result = mealService.createMealDraft(mockImage);

        // Assert
        assertTrue(result.isPresent());
        CreateMealRequest dto = result.get();
        assertEquals(2, dto.getEntries().size());

        verify(inferenceClient).predict(mockImage);
        verify(foodItemRepository).findByTag(101);
        verify(foodItemRepository).findByTag(202);
    }
}