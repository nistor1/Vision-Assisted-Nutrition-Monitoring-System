package org.nutrition.app.meal.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.meal.dto.MealDTO;
import org.nutrition.app.meal.dto.request.CreateMealRequest;
import org.nutrition.app.meal.dto.request.UpdateMealRequest;
import org.nutrition.app.meal.service.MealService;
import org.nutrition.app.util.response.NutritionResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static org.nutrition.app.util.Constants.ReturnMessages.failedToSave;
import static org.nutrition.app.util.Constants.ReturnMessages.notFound;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping
    public NutritionResponse<List<MealDTO>> getMeals() {
        return mealService.findAll()
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(MealDTO.class)),
                        NOT_FOUND));
    }

    @GetMapping("/{id}")
    public NutritionResponse<MealDTO> getMealById(@PathVariable final UUID id) {
        return mealService.findById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(MealDTO.class, "id", id)),
                        NOT_FOUND));
    }

    @PostMapping
    public NutritionResponse<MealDTO> createMealDraft(@RequestParam("image") final MultipartFile image) {
        return mealService.createMealDraft(image)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(failedToSave(MealDTO.class)),
                        BAD_REQUEST));
    }

    @PostMapping("/request")
    public NutritionResponse<MealDTO> createMeal(@RequestBody @Valid final CreateMealRequest request) {
        return mealService.createMeal(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(failedToSave(MealDTO.class)),
                        BAD_REQUEST));
    }


    @PostMapping("/{id}/finalize")
    public NutritionResponse<MealDTO> finalizeMeal(@PathVariable @Valid final UUID id) {
        return mealService.finalizeMeal(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(failedToSave(MealDTO.class)),
                        BAD_REQUEST));
    }

    @PutMapping
    public NutritionResponse<MealDTO> updateMealDraft(
            @RequestBody @Valid final UpdateMealRequest request
    ) {

        return mealService.update(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(MealDTO.class, "id", request.getId())),
                        NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public NutritionResponse<MealDTO> deleteMealById(@PathVariable final UUID id) {
        return mealService.deleteById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(MealDTO.class, "id", id)),
                        NOT_FOUND));
    }

}
