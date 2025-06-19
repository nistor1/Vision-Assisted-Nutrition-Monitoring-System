package org.nutrition.app.food.dto;

import java.io.Serializable;
import java.util.UUID;

public record NutritionProximatesDTO (
        UUID id,
        Double portionSize,
        String unit,
        Double water,
        Double energyGeneral,
        Double energySpecific,
        Double nitrogen,
        Double protein,
        Double totalLipid,
        Double ash
) implements Serializable {
}
