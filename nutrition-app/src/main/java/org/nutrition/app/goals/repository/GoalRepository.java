package org.nutrition.app.goals.repository;

import org.nutrition.app.goals.entity.Goal;
import org.nutrition.app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GoalRepository extends JpaRepository<Goal, UUID> {

    List<Goal> findAllByUser(User user);
}
