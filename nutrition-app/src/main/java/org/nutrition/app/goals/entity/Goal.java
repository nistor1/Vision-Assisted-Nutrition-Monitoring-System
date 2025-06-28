package org.nutrition.app.goals.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.nutrition.app.user.entity.User;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@ToString
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference
    private User user;

    @Column
    private BigDecimal totalCalories;

    @Column
    private BigDecimal totalProteins;

    @Column
    private BigDecimal totalCarbohydrates;

    @Column
    private BigDecimal totalFats;

    @Column
    private BigDecimal totalSugars;

    @Column
    private Double carbohydrate;

    @Column
    private Double fiber;

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

    @Column
    private Double water;

    @Column
    private Double energyGeneral;

    @Column
    private Double energySpecific;

    @Column
    private Double nitrogen;

    @Column
    private Double protein;

    @Column
    private Double totalLipid;

    @Column
    private Double ash;

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
