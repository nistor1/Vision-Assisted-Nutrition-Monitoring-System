package org.nutrition.app.food.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nutrition.app.food.dto.FoodItemDTO;
import org.nutrition.app.food.dto.FoodItemSimpleDTO;
import org.nutrition.app.food.dto.request.create.CreateFoodItemRequest;
import org.nutrition.app.food.dto.request.update.UpdateFoodItemRequest;
import org.nutrition.app.util.response.NutritionResponse;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.food.service.FoodItemService;
import org.nutrition.app.util.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.nutrition.app.util.Constants.ReturnMessages.failedToSave;
import static org.nutrition.app.util.Constants.ReturnMessages.notFound;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/food-items")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    @GetMapping
    public NutritionResponse<PageResponse<FoodItemDTO>> getFoodItems(
            @RequestParam(required = false) String search,
            Pageable pageable) {
        return foodItemService.findAll(search, pageable)
                .map(page -> NutritionResponse.successResponse(new PageResponse<>(page)))
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(FoodItemDTO.class)),
                        NOT_FOUND));
    }

    @GetMapping("/{id}")
    public NutritionResponse<FoodItemDTO> getFoodItemById(@PathVariable final UUID id) {
        return foodItemService.findById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(FoodItemDTO.class, "id", id)),
                        NOT_FOUND));
    }



    @GetMapping("/simple")
    public NutritionResponse<PageResponse<FoodItemSimpleDTO>> getSimpleFoodItems(
            @RequestParam(required = false) String search,
            Pageable pageable) {
        return foodItemService.findAllSimple(search, pageable)
                .map(page -> NutritionResponse.successResponse(new PageResponse<>(page)))
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(FoodItemDTO.class)),
                        NOT_FOUND));
    }

    @GetMapping("/simple/{id}")
    public NutritionResponse<FoodItemSimpleDTO> getSimpleFoodItemById(@PathVariable final UUID id) {
        return foodItemService.findSimpleById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(FoodItemDTO.class, "id", id)),
                        NOT_FOUND));
    }

    @PostMapping
    public NutritionResponse<FoodItemDTO> create(@RequestBody @Valid final CreateFoodItemRequest request) {
        return foodItemService.create(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(failedToSave(FoodItemDTO.class)),
                        BAD_REQUEST));
    }

    @PatchMapping
    public NutritionResponse<FoodItemDTO> update(
            @RequestBody @Valid final UpdateFoodItemRequest request
    ) {

        return foodItemService.update(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(FoodItemDTO.class, "id", request.getId())),
                        NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public NutritionResponse<FoodItemDTO> deleteFoodItemById(@PathVariable final UUID id) {
        return foodItemService.deleteById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(FoodItemDTO.class, "id", id)),
                        NOT_FOUND));
    }
}

