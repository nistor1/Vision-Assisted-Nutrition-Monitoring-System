package org.nutrition.app.meal.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public record SimpleDailyStatisticsResponse (
        LocalDate date,
        BigDecimal totalCalories,
        BigDecimal totalProteins,
        BigDecimal totalFats,
        BigDecimal totalSugars,
        BigDecimal totalCarbs
) implements Serializable {
}
