package org.nutrition.app.meal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.exception.NutritionException;
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
import org.nutrition.app.user.dto.UserDTO;
import org.nutrition.app.user.entity.User;
import org.nutrition.app.util.Constants.Time;
import org.nutrition.app.util.Mapper;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MealService {

    private final MealRepository mealRepository;

    private final FoodItemRepository foodItemRepository;

    private final InferenceClient inferenceClient;

    private final StatisticsUtils nutritionUtils;

    private final AppContext appContext;

    private final CacheManager cacheManager;


    public Optional<Page<MealDTO>> findAll(UUID userId, LocalDate date, Pageable pageable) {
        Page<Meal> meals;

        if (date != null) {

            Date startOfDay = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endOfDay = Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

            meals = mealRepository.findAllByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay, pageable);
        } else {
            meals = mealRepository.findAllByUserId(userId, pageable);
        }

        return Optional.of(meals.map(this::mapMealToDTO));
    }


    public Optional<MealDTO> findById(final UUID id) {
        return mealRepository.findById(id).map(this::mapMealToDTO);
    }

    @Transactional
    public Optional<CreateMealRequest> createMealDraft(final MultipartFile image) {
        List<DetectedObject> detectedObjects = inferenceClient.predict(image);

        if (detectedObjects == null || detectedObjects.isEmpty()) {
            return Optional.empty();
        }

        Meal meal = initializeMealDraft();

        List<MealEntry> entries = detectedObjects.stream()
                .map(detectedObject -> {
                    FoodItem foodItem = findFoodItemByTagOrThrow(detectedObject.getTag());
                    return buildMealEntry(meal, foodItem, detectedObject.getCount());
                })
                .toList();

        meal.setEntries(entries);

        return Optional.of(mapMealToCreateMealRequest(meal));
    }

    @Transactional
    public Optional<MealDTO> createMeal(final CreateMealRequest request) {
        Meal meal = initializeMealDraft();

        Optional.ofNullable(request.getName()).ifPresent(meal::setName);
        Optional.ofNullable(request.getMealType()).ifPresent(meal::setMealType);

        Set<UUID> incomingIds = new HashSet<>();

        for (CreateMealEntryRequest dto : request.getEntries()) {
            UUID foodId = dto.getFoodItemId();
            incomingIds.add(foodId);

            FoodItem food = foodItemRepository.findById(foodId)
                    .orElseThrow(() -> new NutritionException(
                            NutritionError.FOOD_NOT_FOUND, HttpStatus.NOT_FOUND));

            MealEntry newEntry = MealEntry.builder()
                    .withMeal(meal)
                    .withFoodItem(food)
                    .withQuantity(dto.getQuantity())
                    .build();
            meal.addEntry(newEntry);
        }

        meal.getEntries().removeIf(e -> !incomingIds.contains(e.getFoodItem().getId()));

        Meal saved = mealRepository.save(setTotals(meal, nutritionUtils.calculateTotals(meal.getEntries())));

        evictDailyStatistics(saved.getUserId(), saved.getCreatedAt());

        return Optional.of(mapMealToDTO(saved));
    }

    public Optional<MealDTO> finalizeMeal(final UUID id) {
        return mealRepository.findById(id)
                .map(meal -> {
                    meal.setMealStatus(MealStatus.FINALIZED);

                    mealRepository.save(meal);

                    return mapMealToDTO(meal);
                });
    }

    @Transactional
    public Optional<MealDTO> update(UpdateMealRequest request) {
        Meal meal = mealRepository.findById(request.getId())
                .orElseThrow(() -> new NutritionException(NutritionError.MEAL_NOT_FOUND, HttpStatus.NOT_FOUND));


        Optional.ofNullable(request.getName()).ifPresent(meal::setName);
        Optional.ofNullable(request.getMealType()).ifPresent(meal::setMealType);

        Map<UUID, MealEntry> byFoodId = meal.getEntries().stream()
                .collect(Collectors.toMap(
                        e -> e.getFoodItem().getId(),
                        Function.identity()
                ));

        Set<UUID> incomingIds = new HashSet<>();

        for (UpdateMealEntryRequest dto : request.getEntries()) {
            UUID foodId = dto.getFoodItemId();
            incomingIds.add(foodId);

            MealEntry entry = byFoodId.get(foodId);
            if (entry != null) {
                // already on the plate
                if (!entry.getQuantity().equals(dto.getQuantity())) {
                    entry.setQuantity(dto.getQuantity());
                }
            } else {
                // brand-new entry
                FoodItem food = foodItemRepository.findById(foodId)
                        .orElseThrow(() -> new NutritionException(
                                NutritionError.FOOD_NOT_FOUND, HttpStatus.NOT_FOUND));

                MealEntry newEntry = MealEntry.builder()
                        .withMeal(meal)
                        .withFoodItem(food)
                        .withQuantity(dto.getQuantity())
                        .build();
                meal.addEntry(newEntry);
            }
        }

        meal.getEntries().removeIf(e -> !incomingIds.contains(e.getFoodItem().getId()));

        Meal saved = mealRepository.save(setTotals(meal, nutritionUtils.calculateTotals(meal.getEntries())));

        evictDailyStatistics(saved.getUserId(), saved.getCreatedAt());

        return Optional.of(mapMealToDTO(saved));
    }

    @Transactional
    public Optional<MealDTO> deleteById(final UUID id) {
        return mealRepository.findById(id)
                .map(meal -> {
                    mealRepository.delete(meal);

                    evictDailyStatistics(meal.getUserId(), meal.getCreatedAt());

                    return mapMealToDTO(meal);
                });
    }

    private Meal initializeMealDraft() {
        Meal meal = new Meal();

        Date now = Time.now();
        LocalTime time = Instant.ofEpochMilli(now.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
        log.debug("Initializing meal draft at time: {}", time);
        MealType mealType = determineMealType(time);

        meal.setUserId(appContext.getUserId());
        meal.setMealType(mealType);
        meal.setName(generateAutomaticMealName(mealType, time));
        meal.setMealStatus(MealStatus.DRAFT);
        meal.setCreatedAt(Time.now());

        return meal;
    }

    private MealType determineMealType(final LocalTime time) {
        if (time.isAfter(LocalTime.of(5, 0)) && time.isBefore(LocalTime.of(10, 30))) {
            return MealType.BREAKFAST;
        } else if (time.isAfter(LocalTime.of(10, 30)) && time.isBefore(LocalTime.of(14, 30))) {
            return MealType.LUNCH;
        } else if (time.isAfter(LocalTime.of(14, 30)) && time.isBefore(LocalTime.of(18, 0))) {
            return MealType.SNACK;
        } else {
            return MealType.DINNER;
        }
    }

    private String generateAutomaticMealName(final MealType mealType, final LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        log.debug("Generating automatic meal name for type: {}, time: {}", mealType, time);
        return "Meal " + mealType.name() + " " + time.format(formatter);
    }

    private FoodItem findFoodItemByTagOrThrow(final Integer tag) {
        return foodItemRepository.findByTag(tag)
                .orElseThrow(() -> new NutritionException(
                        NutritionError.FOOD_WITH_A_SPECIFIC_TAG_NOT_FOUND(tag.toString()).toString(),
                        HttpStatus.NOT_FOUND
                ));
    }

    private MealEntry buildMealEntry(final Meal meal, final FoodItem foodItem, final Integer count) {
        MealEntry entry = new MealEntry();
        entry.setMeal(meal);
        entry.setFoodItem(foodItem);
        entry.setQuantity(count.doubleValue());
        return entry;
    }

    private Meal setTotals(Meal meal, NutritionTotals totals) {
        meal.setTotalCalories(totals.getCalories());
        meal.setTotalCarbohydrates(totals.getCarbohydrates());
        meal.setTotalProteins(totals.getProteins());
        meal.setTotalFats(totals.getFats());
        meal.setTotalSugars(totals.getSugars());

        return meal;
    }

    private void evictDailyStatistics(UUID userId, Date createdAt) {
        LocalDate date = createdAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String cacheKey = userId + "_" + date;
        cacheManager.getCache("dailyStatistics").evict(cacheKey);
    }

    public UserDTO mapUserToDTO(final User user) {
        return Mapper.mapTo(user, UserDTO.class);
    }

    public MealDTO mapMealToDTO(final Meal meal) {
        return Mapper.mapTo(meal, MealDTO.class);
    }

    public CreateMealRequest mapMealToCreateMealRequest(Meal meal) {
        return CreateMealRequest.builder()
                .withName(meal.getName())
                .withMealType(meal.getMealType())
                .withEntries(meal.getEntries().stream()
                        .map(entry -> new CreateMealEntryRequest(
                                entry.getFoodItem().getId(),
                                entry.getQuantity()))
                        .toList())
                .build();
    }
}
