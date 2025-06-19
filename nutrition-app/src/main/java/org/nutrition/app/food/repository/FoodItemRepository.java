package org.nutrition.app.food.repository;

import org.nutrition.app.food.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, UUID> {

    Optional<FoodItem> findByTag(Integer tag);
}

