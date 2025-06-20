package org.nutrition.app.meal.service.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrition.app.meal.dto.response.DailyStatisticResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.nutrition.app.meal.constants.MealConstants.DAYS_IN_WEEK;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService{

    private final DailyStatisticsService dailyStatisticsService;

    @Override
    public Optional<DailyStatisticResponse> getStatisticsForDay(UUID userId, LocalDate date) {
        return dailyStatisticsService.getStatisticsForDay(userId, date);
    }

    @Override
    public List<DailyStatisticResponse> getStatisticsForWeek(UUID userId) {
        final LocalDate today = LocalDate.now();
        final List<DailyStatisticResponse> weeklyStatistics = new ArrayList<>();

        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            LocalDate currentDate = today.minusDays(i);
            getStatisticsForDay(userId, currentDate)
                    .ifPresent(weeklyStatistics::add);
        }

        weeklyStatistics.sort(Comparator.comparing(DailyStatisticResponse::date));

        return weeklyStatistics;
    }
}
