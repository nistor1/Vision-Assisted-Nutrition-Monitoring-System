package org.nutrition.app.food.repository;

import org.nutrition.app.food.entity.FoodItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, UUID> {

    Page<FoodItem> findByCategory(@NonNull final String category,@NonNull final  Pageable pageable);

    Optional<FoodItem> findByTag(Integer tag);
}

