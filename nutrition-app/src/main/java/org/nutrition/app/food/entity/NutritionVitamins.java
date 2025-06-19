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
public class NutritionVitamins {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Double portionSize;

    @Column
    private String unit;

    @Column(name = "vitamin_a")
    private Double vitaminA;

    @Column(name = "vitamin_b1")
    private Double vitaminB1; // Thiamine

    @Column(name = "vitamin_b2")
    private Double vitaminB2; // Riboflavin

    @Column(name = "vitamin_b3")
    private Double vitaminB3; // Niacin

    @Column(name = "vitamin_b5")
    private Double vitaminB5; // Pantothenic Acid

    @Column(name = "vitamin_b6")
    private Double vitaminB6; // Pyridoxine

    @Column(name = "vitamin_b7")
    private Double vitaminB7; // Biotin

    @Column(name = "vitamin_b9")
    private Double vitaminB9; // Folate

    @Column(name = "vitamin_b12")
    private Double vitaminB12; // Cobalamin

    @Column(name = "vitamin_c")
    private Double vitaminC;

    @Column(name = "vitamin_d")
    private Double vitaminD;

    @Column(name = "vitamin_e")
    private Double vitaminE;

    @Column(name = "vitamin_k")
    private Double vitaminK;

}
