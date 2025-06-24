package org.nutrition.app.goals.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.goals.dto.GoalDTO;
import org.nutrition.app.goals.dto.request.CreateGoalRequest;
import org.nutrition.app.goals.dto.request.UpdateGoalRequest;
import org.nutrition.app.goals.service.GoalService;
import org.nutrition.app.util.response.NutritionResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.nutrition.app.util.Constants.ReturnMessages.failedToSave;
import static org.nutrition.app.util.Constants.ReturnMessages.notFound;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    public NutritionResponse<List<GoalDTO>> getGoals() {
        return goalService.findAll()
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(GoalDTO.class)),
                        NOT_FOUND));
    }

    @GetMapping("/user/{id}")
    public NutritionResponse<List<GoalDTO>> getGoalsByUserId(@PathVariable final UUID id) {
        return goalService.findAllByUserId(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(GoalDTO.class, "id", id)),
                        NOT_FOUND));
    }

    @GetMapping("/{id}")
    public NutritionResponse<GoalDTO> getGoalById(@PathVariable final UUID id) {
        return goalService.findById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(GoalDTO.class, "id", id)),
                        NOT_FOUND));
    }

    @PostMapping
    public NutritionResponse<GoalDTO> create(@RequestBody @Valid final CreateGoalRequest request) {
        return goalService.create(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(failedToSave(GoalDTO.class)),
                        BAD_REQUEST));
    }

    @PutMapping
    public NutritionResponse<GoalDTO> update(
            @RequestBody @Valid final UpdateGoalRequest request
    ) {
        return goalService.update(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(GoalDTO.class, "id", request.id())),
                        NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public NutritionResponse<GoalDTO> deleteGoalById(@PathVariable final UUID id) {
        return goalService.deleteById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(GoalDTO.class, "id", id)),
                        NOT_FOUND));
    }
}
