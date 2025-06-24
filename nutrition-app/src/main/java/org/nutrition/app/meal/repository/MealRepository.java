package org.nutrition.app.meal.repository;

import org.nutrition.app.meal.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface MealRepository extends JpaRepository<Meal, UUID>{

    List<Meal> findAllByUserIdAndCreatedAtBetween(UUID userId, Date start, Date end);


}