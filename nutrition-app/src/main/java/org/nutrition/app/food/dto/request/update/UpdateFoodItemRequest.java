package org.nutrition.app.food.dto.request.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
public class UpdateFoodItemRequest {

    @NotNull(message = NutritionError.ID_IS_REQUIRED)
    private UUID id;

    private Integer tag;

    private String foodName;

    private String category;

    private String imageUrl;

    private Double servingSize;

    private String servingUnit;

    private Boolean active;

    @Valid
    private UpdateNutritionProximatesRequest proximates;

    @Valid
    private UpdateNutritionMineralsRequest minerals;

    @Valid
    private UpdateNutritionCarbohydratesRequest carbohydrates;

    @Valid
    private UpdateNutritionVitaminsRequest vitamins;

}