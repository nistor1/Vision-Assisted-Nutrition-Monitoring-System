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
    private BigDecimal calories = BigDecimal.ZERO;
    private BigDecimal carbohydrates = BigDecimal.ZERO;
    private BigDecimal proteins = BigDecimal.ZERO;

}

