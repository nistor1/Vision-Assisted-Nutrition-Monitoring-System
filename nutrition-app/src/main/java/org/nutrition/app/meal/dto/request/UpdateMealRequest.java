package org.nutrition.app.meal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.meal.constants.MealType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class UpdateMealRequest {

    @NotNull(message = NutritionError.ID_IS_REQUIRED)
    private UUID id;

    private String name;

    private MealType mealType;

    private List<UpdateMealEntryRequest> entries;
}