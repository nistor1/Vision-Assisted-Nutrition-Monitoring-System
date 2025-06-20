package org.nutrition.app.meal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionTotals {

    @Builder.Default
    private BigDecimal calories = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal carbohydrates = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal proteins = BigDecimal.ZERO;

}

