package org.nutrition.app.food.entity;

import java.util.UUID;

public interface FoodItemSimpleProjection {
    UUID getId();
    String getFoodName();
    String getCategory();
    String getImageUrl();
    Double getServingSize();
    String getServingUnit();
    boolean isActive();
}