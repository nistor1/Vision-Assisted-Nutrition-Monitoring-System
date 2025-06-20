package org.nutrition.app.goals.dto.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record CreateGoalRequest (
        UUID userId,
        Date endedAt,
        BigDecimal totalCalories,
        BigDecimal totalProteins,
        BigDecimal totalCarbohydrates,
        BigDecimal totalFats,
        BigDecimal totalSugars
) implements Serializable {
}

