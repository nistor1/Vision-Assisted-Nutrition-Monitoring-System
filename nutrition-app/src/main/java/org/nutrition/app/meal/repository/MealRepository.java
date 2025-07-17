package org.nutrition.app.meal.repository;

import org.nutrition.app.meal.entity.Meal;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface MealRepository extends JpaRepository<Meal, UUID>{

    Page<Meal> findAllByUserId(UUID userId, Pageable pageable);

    List<Meal> findAllByUserIdAndCreatedAtBetween(UUID userId, Date start, Date end);

    Page<Meal> findAllByUserIdAndCreatedAtBetween(UUID userId, Date start, Date end, Pageable pageable);

    void deleteAllByUserId(UUID userId);

}