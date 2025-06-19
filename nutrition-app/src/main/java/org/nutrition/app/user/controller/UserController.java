package org.nutrition.app.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nutrition.app.util.response.NutritionResponse;
import org.nutrition.app.user.dto.UserDTO;
import org.nutrition.app.user.dto.request.CreateUserRequest;
import org.nutrition.app.user.dto.request.UpdateUserRequest;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.user.service.UserService;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public NutritionResponse<List<UserDTO>> getUsers() {
        return userService.findAll()
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(UserDTO.class)),
                        NOT_FOUND));
    }

    @GetMapping("/{id}")
    public NutritionResponse<UserDTO> getUserById(@PathVariable final UUID id) {
        return userService.findById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(UserDTO.class, "id", id)),
                        NOT_FOUND));
    }

    @PostMapping
    public NutritionResponse<UserDTO> create(@RequestBody @Valid final CreateUserRequest request) {
        return userService.create(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(failedToSave(UserDTO.class)),
                        BAD_REQUEST));
    }

    @PutMapping
    public NutritionResponse<UserDTO> update(@RequestBody @Valid final UpdateUserRequest request) {
        return userService.update(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(UserDTO.class, "id", request.getId())),
                        NOT_FOUND));
    }


    @DeleteMapping("/{id}")
    public NutritionResponse<UserDTO> deleteUserById(@PathVariable final UUID id) {
        return userService.deleteById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(UserDTO.class, "id", id)),
                        NOT_FOUND));
    }
}

