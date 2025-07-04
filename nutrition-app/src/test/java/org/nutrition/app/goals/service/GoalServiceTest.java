package org.nutrition.app.goals.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nutrition.app.exception.NutritionException;
import org.nutrition.app.goals.dto.request.CreateGoalRequest;
import org.nutrition.app.goals.dto.request.UpdateGoalRequest;
import org.nutrition.app.goals.entity.Goal;
import org.nutrition.app.goals.repository.GoalRepository;
import org.nutrition.app.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.nutrition.app.util.TestUtils.GoalUtils.randomGoal;
import static org.nutrition.app.util.TestUtils.UserUtils.randomUser;


@ExtendWith(MockitoExtension.class)
class GoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GoalService goalService;

    @Test
    void testFindAll_shouldReturnListOfGoalDTOs() {
        var goal = randomGoal();
        when(goalRepository.findAll()).thenReturn(List.of(goal));

        var result = goalService.findAll();

        verify(goalRepository).findAll();
        assertTrue(result.isPresent());
        assertEquals(1, result.get().size());
        assertEquals(goal.getUser().getId(), result.get().getFirst().getUserId());
    }

    @Test
    void testFindById_goalFound_shouldReturnDTO() {
        var goal = randomGoal();
        when(goalRepository.findById(goal.getId())).thenReturn(Optional.of(goal));

        var result = goalService.findById(goal.getId());

        verify(goalRepository).findById(goal.getId());
        assertTrue(result.isPresent());
        assertEquals(goal.getUser().getId(), result.get().getUserId());
    }

    @Test
    void testFindById_goalNotFound_shouldReturnEmpty() {
        UUID id = UUID.randomUUID();
        when(goalRepository.findById(id)).thenReturn(Optional.empty());

        var result = goalService.findById(id);

        verify(goalRepository).findById(id);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByUserId_userAndGoalExist_shouldReturnDTO() {
        var goal = randomGoal();
        var user = goal.getUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(goalRepository.findByUser(user)).thenReturn(Optional.of(goal));

        var result = goalService.findByUserId(user.getId());

        verify(userRepository).findById(user.getId());
        verify(goalRepository).findByUser(user);
        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getUserId());
    }

    @Test
    void testFindByUserId_userNotFound_shouldThrowException() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NutritionException.class, () -> goalService.findByUserId(userId));
    }

    @Test
    void testCreate_validRequest_shouldCreateGoalAndReturnDTO() {
        var goal = randomGoal();
        var user = goal.getUser();
        var request = new CreateGoalRequest(user.getId());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(goalRepository.save(any(Goal.class))).thenReturn(goal);

        var result = goalService.create(request);

        verify(userRepository).findById(user.getId());
        verify(goalRepository).save(any(Goal.class));
        assertTrue(result.isPresent());
        assertEquals(user.getId(), result.get().getUserId());
    }

    @Test
    void testCreate_userNotFound_shouldThrowException() {
        UUID userId = UUID.randomUUID();
        var request = new CreateGoalRequest(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NutritionException.class, () -> goalService.create(request));
    }

    @Test
    void testUpdate_goalFound_shouldUpdateAndReturnDTO() {
        var goal = randomGoal();
        var request = new UpdateGoalRequest();
        request.setId(goal.getId());

        when(goalRepository.findById(goal.getId())).thenReturn(Optional.of(goal));
        when(goalRepository.save(goal)).thenReturn(goal);

        var result = goalService.update(request);

        verify(goalRepository).findById(goal.getId());
        verify(goalRepository).save(goal);
        assertTrue(result.isPresent());
    }

    @Test
    void testUpdate_goalNotFound_shouldReturnEmpty() {
        UUID id = UUID.randomUUID();
        var request = new UpdateGoalRequest();
        request.setId(id);

        when(goalRepository.findById(id)).thenReturn(Optional.empty());

        var result = goalService.update(request);

        verify(goalRepository).findById(id);
        verify(goalRepository, never()).save(any());
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteById_goalFound_shouldDeleteAndReturnDTO() {
        var goal = randomGoal();

        when(goalRepository.findById(goal.getId())).thenReturn(Optional.of(goal));

        var result = goalService.deleteById(goal.getId());

        verify(goalRepository).findById(goal.getId());
        verify(goalRepository).delete(goal);
        assertTrue(result.isPresent());
    }

    @Test
    void testDeleteById_goalNotFound_shouldReturnEmpty() {
        UUID id = UUID.randomUUID();
        when(goalRepository.findById(id)).thenReturn(Optional.empty());

        var result = goalService.deleteById(id);

        verify(goalRepository).findById(id);
        verify(goalRepository, never()).delete(any());
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteByUser_userExists_shouldCallDelete() {
        var user = randomUser();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        goalService.deleteByUser(user.getId());

        verify(userRepository).findById(user.getId());
        verify(goalRepository).deleteByUser(user);
    }

    @Test
    void testDeleteByUser_userNotFound_shouldNotCallDelete() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        goalService.deleteByUser(id);

        verify(userRepository).findById(id);
        verify(goalRepository, never()).deleteByUser(any());
    }
}
