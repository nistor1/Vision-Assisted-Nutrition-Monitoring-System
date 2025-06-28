package org.nutrition.app.goals.dto.request;

import java.io.Serializable;
import java.util.UUID;

public record CreateGoalRequest (
        UUID userId
) implements Serializable {
}

