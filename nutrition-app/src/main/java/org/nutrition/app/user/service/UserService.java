package org.nutrition.app.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrition.app.goals.dto.request.CreateGoalRequest;
import org.nutrition.app.goals.service.GoalService;
import org.nutrition.app.user.dto.request.CreateUserRequest;
import org.nutrition.app.user.dto.request.UpdateUserRequest;
import org.nutrition.app.user.dto.UserDTO;
import org.nutrition.app.user.entity.User;
import org.nutrition.app.user.repository.UserRepository;
import org.nutrition.app.util.Mapper;
import org.nutrition.app.util.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final GoalService goalService;
    private final PasswordEncoder passwordEncoder;

    public Optional<Page<UserDTO>> findAll(String search, Pageable pageable) {
        Page<User> users;

        if ("ADMIN".equalsIgnoreCase(search)) {
            users = userRepository.findByRole(Role.ADMIN, pageable);
        } else if ("USER".equalsIgnoreCase(search)) {
            users = userRepository.findByRole(Role.USER, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }

        return Optional.of(users.map(this::mapToDTO));
    }


    public Optional<UserDTO> findById(final UUID id) {
        return userRepository.findById(id).map(this::mapToDTO);
    }

    public Optional<UserDTO> create(final CreateUserRequest request) {
        var role = request.getRole() != null ? request.getRole() : Role.USER;

        var user = User.builder()
                .withUsername(request.getUsername())
                .withEmail(request.getEmail())
                .withPassword(passwordEncoder.encode(request.getPassword()))
                .withRole(role)
                .withFullName(request.getFullName())
                .withPhoneNumber(request.getPhoneNumber())
                .withAddress(request.getAddress())
                .withCity(request.getCity())
                .withPostalCode(request.getPostalCode())
                .withCountry(request.getCountry())
                .build();

        user = userRepository.save(user);

        var goal = new CreateGoalRequest(user.getId());
        goalService.create(goal);

        return Optional.of(mapToDTO(user));
    }

    public Optional<UserDTO> update(final UpdateUserRequest request) {
        return userRepository.findById(request.getId())
                .map(user -> {
                    Mapper.updateValues(user, request);

                    userRepository.save(user);

                    return mapToDTO(user);
                });
    }

    public Optional<UserDTO> deleteById(final UUID id) {
        goalService.deleteByUser(id);
        return userRepository.findById(id)
                .filter(user -> userRepository.deleteByIdReturning(id) != 0)
                .map(this::mapToDTO);
    }

    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<UserDTO> findByEmail(final String email) {
        return userRepository.findByEmail(email).map(this::mapToDTO);
    }

    public boolean updateUserPassword(final UUID id, final String newPassword) {
        return userRepository.updateUserPassword(id, passwordEncoder.encode(newPassword)) != 0;
    }

    public UserDTO mapToDTO(final User user) {
        return Mapper.mapTo(user, UserDTO.class);
    }
}