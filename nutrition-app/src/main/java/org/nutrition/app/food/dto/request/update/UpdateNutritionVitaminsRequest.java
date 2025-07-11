package org.nutrition.app.food.dto.request.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutrition.app.exception.NutritionError;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class UpdateNutritionVitaminsRequest {

    @NotNull(message = NutritionError.ID_IS_REQUIRED)
    private UUID id;

    @Positive(message = NutritionError.PORTION_SIZE_MUST_BE_POSITIVE)
    private Double portionSize;

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
