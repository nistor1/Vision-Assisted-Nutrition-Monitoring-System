package org.nutrition.app.goals.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.exception.NutritionException;
import org.nutrition.app.goals.dto.GoalDTO;
import org.nutrition.app.goals.dto.request.CreateGoalRequest;
import org.nutrition.app.goals.dto.request.UpdateGoalRequest;
import org.nutrition.app.goals.entity.Goal;
import org.nutrition.app.goals.repository.GoalRepository;
import org.nutrition.app.user.dto.UserDTO;
import org.nutrition.app.user.entity.User;
import org.nutrition.app.user.repository.UserRepository;
import org.nutrition.app.util.Constants.Time;
import org.nutrition.app.util.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    public Optional<List<GoalDTO>> findAll() {
        return Optional.of(goalRepository.findAll().stream().map(this::mapGoalToDTO).toList());
    }

    public Optional<List<GoalDTO>> findAllByUserId(final UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new NutritionException(NutritionError.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND)
                );
        return Optional.of(goalRepository.findAllByUser(user).stream().map(this::mapGoalToDTO).toList());
    }

    public Optional<GoalDTO> findById(final UUID id) {
        return goalRepository.findById(id).map(this::mapGoalToDTO);
    }

    public Optional<GoalDTO> create(final CreateGoalRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() ->
                        new NutritionException(NutritionError.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND)
                );

        var goal = Goal.builder()
                .withCreatedAt(Time.now())
                .withEndedAt(request.endedAt())
                .withTotalSugars(request.totalSugars())
                .withTotalCalories(request.totalCalories())
                .withTotalCarbohydrates(request.totalCarbohydrates())
                .withTotalFats(request.totalFats())
                .withTotalProteins(request.totalProteins())
                .withUser(user)
                .build();

        var saved = goalRepository.save(goal);
        return Optional.of(mapGoalToDTO(saved));
    }

    public Optional<GoalDTO> update(final UpdateGoalRequest request) {
        return goalRepository.findById(request.id())
                .map(foodItem -> {
                    Mapper.updateValues(foodItem, request);

                    goalRepository.save(foodItem);

                    return mapGoalToDTO(foodItem);
                });
    }

    @Transactional
    public Optional<GoalDTO> deleteById(final UUID id) {
        return goalRepository.findById(id)
                .map(foodItem -> {
                    goalRepository.delete(foodItem);
                    return mapGoalToDTO(foodItem);
                });
    }

    public UserDTO mapUserToDTO(final User user) {
        return Mapper.mapTo(user, UserDTO.class);
    }

    public GoalDTO mapGoalToDTO(final Goal goal) {
        GoalDTO goalDTO =  Mapper.mapTo(goal, GoalDTO.class);
        goalDTO.setUser(mapUserToDTO(goal.getUser()));
        return goalDTO;
    }
}
