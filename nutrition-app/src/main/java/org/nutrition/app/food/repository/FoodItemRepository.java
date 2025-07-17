package org.nutrition.app.food.repository;

import org.nutrition.app.food.entity.FoodItem;
import org.nutrition.app.food.entity.FoodItemSimpleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, UUID> {

    Page<FoodItem> findByCategory(@NonNull final String category,@NonNull final  Pageable pageable);

    Optional<FoodItem> findByTag(Integer tag);

    @Query("""
    SELECT f.id AS id, f.foodName AS foodName, f.category AS category,
           f.imageUrl AS imageUrl, f.servingSize AS servingSize,
           f.servingUnit AS servingUnit, f.active AS active
    FROM FoodItem f
    WHERE f.category LIKE %:category%
""")
    Page<FoodItemSimpleProjection> findSimpleByCategory(@NonNull String category, @NonNull Pageable pageable);

    @Query("""
    SELECT f.id AS id, f.foodName AS foodName, f.category AS category,
           f.imageUrl AS imageUrl, f.servingSize AS servingSize,
           f.servingUnit AS servingUnit, f.active AS active
    FROM FoodItem f
""")
    Page<FoodItemSimpleProjection> findAllSimple(Pageable pageable);

    @Query("""
    SELECT f.id AS id, f.foodName AS foodName, f.category AS category,
           f.imageUrl AS imageUrl, f.servingSize AS servingSize,
           f.servingUnit AS servingUnit, f.active AS active
    FROM FoodItem f
    WHERE f.id = :id
""")
    Optional<FoodItemSimpleProjection> findSimpleById(@NonNull UUID id);
}

