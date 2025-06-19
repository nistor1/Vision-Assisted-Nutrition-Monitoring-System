package org.nutrition.app.meal.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix="with")
public class UpdateMealEntryRequest {

    @NotNull
    private UUID foodItemId;

    @NotNull
    @Positive
    private Double quantity;
}
