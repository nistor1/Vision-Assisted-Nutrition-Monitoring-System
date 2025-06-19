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
public class CreateNutritionVitaminsRequest {

    @Positive(message = NutritionError.PORTION_SIZE_MUST_BE_POSITIVE)
    private Double portionSize;

    @NotBlank(message = NutritionError.UNIT_IS_REQUIRED)
    private String unit;

    private Double vitaminA;

    private Double vitaminB1;

    private Double vitaminB2;

    private Double vitaminB3;

    private Double vitaminB5;

    private Double vitaminB6;

    private Double vitaminB7;

    private Double vitaminB9;

    private Double vitaminB12;

    private Double vitaminC;

    private Double vitaminD;

    private Double vitaminE;

    private Double vitaminK;
}
