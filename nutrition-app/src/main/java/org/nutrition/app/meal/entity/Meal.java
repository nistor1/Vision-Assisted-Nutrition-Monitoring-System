package org.nutrition.app.meal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.nutrition.app.meal.constants.MealStatus;
import org.nutrition.app.meal.constants.MealType;
import org.nutrition.app.user.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
@ToString
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealStatus mealStatus;

    @Column(nullable = false)
    private Date createdAt;

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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<MealEntry> entries = new ArrayList<>();

    public void addEntry(MealEntry entry) {
        entries.add(entry);
        entry.setMeal(this);
    }

    public void removeEntry(MealEntry entry) {
        entries.remove(entry);
        entry.setMeal(null);
    }
}
