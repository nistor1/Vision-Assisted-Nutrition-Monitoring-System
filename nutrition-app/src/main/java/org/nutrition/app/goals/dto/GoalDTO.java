package org.nutrition.app.goals.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nutrition.app.user.dto.UserDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class GoalDTO implements Serializable {

    private UUID id;

    private UserDTO user;

    private Date createdAt;

    private Date endedAt;

    private BigDecimal totalCalories;

    private BigDecimal totalProteins;

    private BigDecimal totalCarbohydrates;

    private BigDecimal totalFats;

    private BigDecimal totalSugars;
}
