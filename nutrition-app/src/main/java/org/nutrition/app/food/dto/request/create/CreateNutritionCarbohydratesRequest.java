package org.nutrition.app.food.dto.request.create;

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
public class CreateNutritionCarbohydratesRequest {

    @Positive(message = NutritionError.PORTION_SIZE_MUST_BE_POSITIVE)
    private Double portionSize;

    @NotBlank(message = NutritionError.UNIT_IS_REQUIRED)
    private String unit;

    private Double carbohydrate;

    private Double fiber;

    private Double totalSugars;

    private Double sucrose;

    private Double glucose;

    private Double fructose;

    private Double maltose;

    private Double lactose;
}
