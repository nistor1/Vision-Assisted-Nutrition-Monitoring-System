package org.nutrition.app.meal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.meal.dto.MealDTO;
import org.nutrition.app.meal.dto.request.CreateMealRequest;
import org.nutrition.app.meal.dto.request.UpdateMealRequest;
import org.nutrition.app.meal.dto.response.DailyStatisticResponse;
import org.nutrition.app.meal.service.MealService;
import org.nutrition.app.meal.service.statistics.StatisticsService;
import org.nutrition.app.security.config.AppContext;
import org.nutrition.app.util.response.NutritionResponse;
import org.nutrition.app.util.response.PageResponse;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.nutrition.app.util.Constants.ReturnMessages.failedToSave;
import static org.nutrition.app.util.Constants.ReturnMessages.notFound;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
@Slf4j
public class MealController {

    private final MealService mealService;
    private final AppContext appContext;
    private final StatisticsService statisticsService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public NutritionResponse<PageResponse<MealDTO>> getMealsByDate(
            final @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("Fetching meals for user {} on date {}", appContext.getUserId(), date);
        return mealService.findAll(appContext.getUserId(), date, pageable)
                .map(page -> NutritionResponse.successResponse(new PageResponse<>(page)))
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(MealDTO.class)),
                        NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public NutritionResponse<MealDTO> getMealById(@PathVariable final UUID id) {
        return mealService.findById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(MealDTO.class, "id", id)),
                        NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public NutritionResponse<CreateMealRequest> createMealDraft(@RequestParam("image") final MultipartFile image) {
        return mealService.createMealDraft(image)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(failedToSave(MealDTO.class)),
                        BAD_REQUEST));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/request")
    public NutritionResponse<MealDTO> createMeal(@RequestBody @Valid final CreateMealRequest request) {
        return mealService.createMeal(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(failedToSave(MealDTO.class)),
                        BAD_REQUEST));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping("/{id}/finalize")
    public NutritionResponse<MealDTO> finalizeMeal(@PathVariable @Valid final UUID id) {
        return mealService.finalizeMeal(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(failedToSave(MealDTO.class)),
                        BAD_REQUEST));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping
    public NutritionResponse<MealDTO> updateMealDraft(
            @RequestBody @Valid final UpdateMealRequest request
    ) {

        return mealService.update(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(MealDTO.class, "id", request.getId())),
                        NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public NutritionResponse<MealDTO> deleteMealById(@PathVariable final UUID id) {
        return mealService.deleteById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(MealDTO.class, "id", id)),
                        NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/statistics/day")
    public NutritionResponse<DailyStatisticResponse> getStatisticsForDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate date) {
        return statisticsService.getStatisticsForDay(appContext.getUserId(), date)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(DailyStatisticResponse.class, "date", date)),
                        NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/statistics/week")
    public NutritionResponse<List<DailyStatisticResponse>> getStatisticsForWeek() {
        final List<DailyStatisticResponse> stats = statisticsService.getStatisticsForWeek(appContext.getUserId());

        if (stats.isEmpty()) {
            return NutritionResponse.failureResponse(
                    new NutritionError(NutritionError.NO_MEALS_FOUND_IN_THE_LAST_WEEK),
                    NOT_FOUND);
        }

        return NutritionResponse.successResponse(stats);
    }
}
