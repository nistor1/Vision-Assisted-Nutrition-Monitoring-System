package org.nutrition.app.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nutrition.app.goals.service.GoalService;
import org.nutrition.app.user.dto.UserDTO;
import org.nutrition.app.user.entity.User;
import org.nutrition.app.user.repository.UserRepository;
import org.nutrition.app.util.Mapper;
import org.nutrition.app.util.Role;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.nutrition.app.util.TestUtils.UserUtils.createUserRequest;
import static org.nutrition.app.util.TestUtils.UserUtils.randomUser;
import static org.nutrition.app.util.TestUtils.UserUtils.updateUserRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest  {

    @Mock
    private GoalService goalService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private UserService victim;

    @Test
    void testFindAll_withAdminSearch_shouldReturnAdmins() {
        var user = randomUser();
        var page = new PageImpl<>(List.of(user));
        var pageable = Pageable.unpaged();

        when(userRepository.findByRole(Role.ADMIN, pageable)).thenReturn(page);

        var result = victim.findAll("ADMIN", pageable);

        verify(userRepository).findByRole(Role.ADMIN, pageable);
        assertTrue(result.isPresent());
        assertThat(result.get().getContent(), hasItem(Mapper.mapTo(user, UserDTO.class)));
    }

    @Test
    void testFindAll_withUserSearch_shouldReturnUsers() {
        var user = randomUser();
        var page = new PageImpl<>(List.of(user));
        var pageable = Pageable.unpaged();

        when(userRepository.findByRole(Role.USER, pageable)).thenReturn(page);

        var result = victim.findAll("USER", pageable);

        verify(userRepository).findByRole(Role.USER, pageable);
        assertTrue(result.isPresent());
        assertThat(result.get().getContent(), hasItem(Mapper.mapTo(user, UserDTO.class)));
    }

    @Test
    void testFindAll_withNoSearch_shouldReturnAll() {
        var user = randomUser();
        var page = new PageImpl<>(List.of(user));
        var pageable = Pageable.unpaged();

        when(userRepository.findAll(pageable)).thenReturn(page);

        var result = victim.findAll(null, pageable);

        verify(userRepository).findAll(pageable);
        assertTrue(result.isPresent());
        assertThat(result.get().getContent(), hasItem(Mapper.mapTo(user, UserDTO.class)));
    }

    @Test
    void testFindByEmail_userExists_shouldReturnUserDTO() {
        var user = randomUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        var result = victim.findByEmail(user.getEmail());

        verify(userRepository).findByEmail(user.getEmail());
        assertTrue(result.isPresent());
        assertThat(result.get(), equalTo(Mapper.mapTo(user, UserDTO.class)));
    }

    @Test
    void testFindByEmail_userNotFound_shouldReturnEmpty() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        var result = victim.findByEmail("nonexistent@example.com");

        verify(userRepository).findByEmail(anyString());
        assertTrue(result.isEmpty());
    }

    @Test
    void testCreate_shouldSaveAndReturnUserDTO() {
        var userToCreate = randomUser();
        var request = createUserRequest(userToCreate);
        var user = Mapper.mapTo(request, User.class);
        user.setId(UUID.randomUUID());

        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userRepository.save(any(User.class))).thenReturn(user);

        var result = victim.create(request);

        verify(userRepository).save(any(User.class));
        assertTrue(result.isPresent());
        assertThat(result.get().username(), equalTo(request.getUsername()));
    }

    @Test
    void testUpdate_userFound_shouldUpdateAndReturnDTO() {
        var user = randomUser();
        var request = updateUserRequest(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        var result = victim.update(request);

        verify(userRepository).findById(user.getId());
        verify(userRepository).save(user);
        assertTrue(result.isPresent());
        assertThat(result.get().id(), equalTo(user.getId()));
    }

    @Test
    void testUpdate_userNotFound_shouldReturnEmpty() {
        var request = updateUserRequest(randomUser());
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        var result = victim.update(request);

        verify(userRepository).findById(any());
        verify(userRepository, never()).save(any());
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteById_userFoundAndDeleted_shouldReturnDTO() {
        var user = randomUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.deleteByIdReturning(user.getId())).thenReturn(1);

        var result = victim.deleteById(user.getId());

        verify(userRepository).deleteByIdReturning(user.getId());
        assertTrue(result.isPresent());
        assertThat(result.get(), equalTo(Mapper.mapTo(user, UserDTO.class)));
    }

    @Test
    void testDeleteById_userNotDeleted_shouldReturnEmpty() {
        var user = randomUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.deleteByIdReturning(user.getId())).thenReturn(0);

        var result = victim.deleteById(user.getId());

        verify(userRepository).deleteByIdReturning(user.getId());
        assertTrue(result.isEmpty());
    }


}