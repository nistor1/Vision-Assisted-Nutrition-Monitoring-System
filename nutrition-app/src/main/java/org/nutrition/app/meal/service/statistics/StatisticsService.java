package org.nutrition.app.meal.service.statistics;

import org.nutrition.app.meal.dto.response.DailyStatisticResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StatisticsService {

    /**
     * Returns the nutritional statistics for a specific day.
     *
     * @param userId the UUID of the user
     * @param date the day for which statistics are requested
     * @return an Optional containing statistics for the given day, or empty if no data exists
     */
    Optional<DailyStatisticResponse> getStatisticsForDay(UUID userId, LocalDate date);

    /**
     * Returns a list of daily statistics for the past 7 days (including today).
     *
     * @param userId the UUID of the user
     * @return a list of DailyStatisticsDTO (up to 7 items, sorted descending by date)
     */
    List<DailyStatisticResponse> getStatisticsForWeek(UUID userId);
}
