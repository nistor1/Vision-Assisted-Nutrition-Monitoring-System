package org.nutrition.app.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nutrition.app.security.config.AppContext;
import org.nutrition.app.user.dto.request.UpdateUserPersonalRequest;
import org.nutrition.app.util.response.NutritionResponse;
import org.nutrition.app.user.dto.UserDTO;
import org.nutrition.app.user.dto.request.CreateUserRequest;
import org.nutrition.app.user.dto.request.UpdateUserRequest;
import org.nutrition.app.exception.NutritionError;
import org.nutrition.app.user.service.UserService;
import org.nutrition.app.util.response.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private final AppContext appContext;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public NutritionResponse<PageResponse<UserDTO>> getUsers(
            @RequestParam(required = false) String search,
            Pageable pageable) {
        return userService.findAll(search, pageable)
                .map(page -> NutritionResponse.successResponse(new PageResponse<>(page)))
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(UserDTO.class)),
                        NOT_FOUND
                ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public NutritionResponse<UserDTO> getUserById(@PathVariable final UUID id) {
        return userService.findById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(UserDTO.class, "id", id)),
                        NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/personal")
    public NutritionResponse<UserDTO> getUserPersonal() {
        return userService.findById(appContext.getUserId())
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(UserDTO.class, "id", appContext.getUserId())),
                        NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public NutritionResponse<UserDTO> create(@RequestBody @Valid final CreateUserRequest request) {
        return userService.create(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(failedToSave(UserDTO.class)),
                        BAD_REQUEST));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping
    public NutritionResponse<UserDTO> update(@RequestBody @Valid final UpdateUserRequest request) {
        return userService.update(request)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(UserDTO.class, "id", request.getId())),
                        NOT_FOUND));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping("/personal")
    public NutritionResponse<UserDTO> updatePersonal(@RequestBody @Valid final UpdateUserPersonalRequest request) {
        return userService.updatePersonal(request, appContext.getUserId())
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(UserDTO.class, "id", appContext.getUserId())),
                        NOT_FOUND));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public NutritionResponse<UserDTO> deleteUserById(@PathVariable final UUID id) {
        return userService.deleteById(id)
                .map(NutritionResponse::successResponse)
                .orElse(NutritionResponse.failureResponse(
                        new NutritionError(notFound(UserDTO.class, "id", id)),
                        NOT_FOUND));
    }
}

