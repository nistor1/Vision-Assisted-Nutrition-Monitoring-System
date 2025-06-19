package org.nutrition.app.food.dto.request.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutrition.app.exception.NutritionError;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class CreateFoodItemRequest {

    private Integer tag;

    @NotBlank(message = NutritionError.FOOD_NAME_IS_REQUIRED)
    private String foodName;

    @NotBlank(message = NutritionError.FOOD_CATEGORY_IS_REQUIRED)
    private String category;

    private String imageUrl;

    @Positive(message = NutritionError.SERVING_SIZE_MUST_BE_POSITIVE)
    private Double servingSize;

    @NotBlank(message = NutritionError.UNIT_IS_REQUIRED)
    private String servingUnit;

    private Boolean active;

    @Valid
    private CreateNutritionProximatesRequest proximates;

    @Valid
    private CreateNutritionMineralsRequest minerals;

    @Valid
    private CreateNutritionCarbohydratesRequest carbohydrates;

    @Valid
    private CreateNutritionVitaminsRequest vitamins;

}