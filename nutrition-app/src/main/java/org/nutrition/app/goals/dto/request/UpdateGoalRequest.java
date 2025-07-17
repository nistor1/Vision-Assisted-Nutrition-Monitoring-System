package org.nutrition.app.goals.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class UpdateGoalRequest implements Serializable {

    private UUID id;

    private BigDecimal totalCalories;

    private BigDecimal totalProteins;

    private BigDecimal totalCarbohydrates;

    private BigDecimal totalFats;

    private BigDecimal totalSugars;

    private Double carbohydrate;
    private Double fiber;
    private Double sucrose;
    private Double glucose;
    private Double fructose;
    private Double maltose;
    private Double lactose;

    private Double calcium;
    private Double iron;
    private Double magnesium;
    private Double phosphorus;
    private Double potassium;
    private Double sodium;
    private Double zinc;
    private Double copper;
    private Double manganese;

    private Double water;
    private Double energyGeneral;
    private Double energySpecific;
    private Double nitrogen;
    private Double protein;
    private Double totalLipid;
    private Double ash;

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