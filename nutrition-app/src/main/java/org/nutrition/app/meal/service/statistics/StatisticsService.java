package org.nutrition.app.meal.service.statistics;

import org.nutrition.app.meal.dto.response.DailyStatisticResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StatisticsService {

    Optional<DailyStatisticResponse> getStatisticsForDay(UUID userId, LocalDate date);

    List<DailyStatisticResponse> getStatisticsForWeek(UUID userId);
}
