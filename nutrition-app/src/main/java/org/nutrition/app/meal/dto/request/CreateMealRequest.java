package org.nutrition.app.meal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutrition.app.meal.constants.MealType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class CreateMealRequest {

    private String name;

    private MealType mealType;

    private List<CreateMealEntryRequest> entries;
}
