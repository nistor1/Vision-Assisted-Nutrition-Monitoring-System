package org.nutrition.app.goals.repository;

import org.nutrition.app.goals.entity.Goal;
import org.nutrition.app.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GoalRepository extends JpaRepository<Goal, UUID> {

    Optional<Goal> findByUser(User user);
    void deleteByUser(User user);

}
