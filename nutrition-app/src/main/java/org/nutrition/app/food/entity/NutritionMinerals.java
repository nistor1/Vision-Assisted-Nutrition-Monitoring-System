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
public class NutritionMinerals {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Double portionSize;

    @Column
    private String unit;

    @Column
    private Double calcium;

    @Column
    private Double iron;

    @Column
    private Double magnesium;

    @Column
    private Double phosphorus;

    @Column
    private Double potassium;

    @Column
    private Double sodium;

    @Column
    private Double zinc;

    @Column
    private Double copper;

    @Column
    private Double manganese;

}
