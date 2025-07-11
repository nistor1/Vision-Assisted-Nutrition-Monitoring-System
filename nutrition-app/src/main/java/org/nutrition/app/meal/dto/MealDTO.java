package org.nutrition.app.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutrition.app.meal.constants.MealStatus;
import org.nutrition.app.meal.constants.MealType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class MealDTO implements Serializable{
    private UUID id;
    private UUID userId;
    private String name;
    private MealType mealType;
    private MealStatus mealStatus;
    private Date createdAt;
    private BigDecimal totalCalories;
    private BigDecimal totalProteins;
    private BigDecimal totalCarbohydrates;
    private BigDecimal totalFats;
    private BigDecimal totalSugars;
    private List<MealEntryDTO> entries;
}

