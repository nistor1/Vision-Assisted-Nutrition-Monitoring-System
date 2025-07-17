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
import org.nutrition.app.user.entity.User;
import org.nutrition.app.user.repository.UserRepository;
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

    public Optional<GoalDTO> findByUserId(final UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new NutritionException(NutritionError.USER_NOT_FOUND.toString(), HttpStatus.NOT_FOUND)
                );
        return goalRepository.findByUser(user).map(this::mapGoalToDTO);
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
                .withUser(user)
                .build();

        var saved = goalRepository.save(goal);
        return Optional.of(mapGoalToDTO(saved));
    }

    public Optional<GoalDTO> update(final UpdateGoalRequest request) {
        return goalRepository.findById(request.getId())
                .map(goal -> {
                    Mapper.updateValues(goal, request);

                    goalRepository.save(goal);

                    return mapGoalToDTO(goal);
                });
    }

    @Transactional
    public Optional<GoalDTO> deleteById(final UUID id) {
        return goalRepository.findById(id)
                .map(goal -> {
                    goalRepository.delete(goal);
                    return mapGoalToDTO(goal);
                });
    }

    @Transactional
    public void deleteByUser(final UUID id) {
        userRepository.findById(id)
                .ifPresent(goalRepository::deleteByUser);
    }

    public GoalDTO mapGoalToDTO(final Goal goal) {
        GoalDTO goalDTO =  Mapper.mapTo(goal, GoalDTO.class);
        goalDTO.setUserId(goal.getUser().getId());
        return goalDTO;
    }
}
