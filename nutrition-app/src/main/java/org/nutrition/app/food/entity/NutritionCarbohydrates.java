package org.nutrition.app.food.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@ToString
public class NutritionCarbohydrates {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Double portionSize;

    @Column
    private String unit;

    @Column
    private Double carbohydrate;

    @Column
    private Double fiber;

    @Column
    private Double totalSugars;

    @Column
    private Double sucrose;

    @Column
    private Double glucose;

    @Column
    private Double fructose;

    @Column
    private Double maltose;

    @Column
    private Double lactose;

}
