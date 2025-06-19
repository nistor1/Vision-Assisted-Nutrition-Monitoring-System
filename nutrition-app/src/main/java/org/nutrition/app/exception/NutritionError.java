package org.nutrition.app.exception;

import java.io.Serializable;

public record NutritionError(String message) implements Serializable {
    public static final NutritionError INTERNAL_SERVER_ERROR = new NutritionError("Internal Server Error");
    public static final NutritionError BAD_CREDENTIAL_EXCEPTION = new NutritionError("Invalid username or password");
    public static final NutritionError REGISTRATION_FAILED = new NutritionError("Registration failed");
    public static final NutritionError BAD_TOKEN = new NutritionError("Malformed token");
    public static final NutritionError FORBIDDEN = new NutritionError("Access denied");
    public static final NutritionError PASSWORD_RESET_FAIL = new NutritionError("Password reset failed");

    // === User Errors ===
    public static final NutritionError USER_NOT_FOUND = new NutritionError("User not found");
    public static final NutritionError USER_UPDATE_FAILED = new NutritionError("Failed to update user");
    public static final NutritionError USER_DELETE_FAILED = new NutritionError("Failed to delete user");
    public static final NutritionError USER_CREATE_FAILED = new NutritionError("Failed to create user");

    // === User Validation ===
    public static final String USERNAME_REQUIRED = "Username is required";
    public static final String USERNAME_LENGTH = "Username must be between 3 and 50 characters";

    public static final String PASSWORD_REQUIRED = "Password is required";
    public static final String PASSWORD_TOO_SHORT = "Password must be at least 6 characters";

    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String EMAIL_INVALID = "Email must be valid";

    public static final String ROLE_REQUIRED = "Role is required";

    // === Food Errors ===
    public static final String UNIT_IS_REQUIRED = "Unit is required";
    public static final String PORTION_SIZE_MUST_BE_POSITIVE = "Portion size must be positive";
    public static final String FOOD_NAME_IS_REQUIRED = "Food name is required";
    public static final String FOOD_CATEGORY_IS_REQUIRED = "Food category is required";
    public static final String SERVING_SIZE_MUST_BE_POSITIVE = "Serving size must be positive";
    public static final String ID_IS_REQUIRED = "ID is required";
    public static final String FOOD_WITH_A_SPECIFIC_TAG_NOT_FOUND_TEMPLATE = "FoodItem with tag %s not found";
    public static NutritionError FOOD_WITH_A_SPECIFIC_TAG_NOT_FOUND(String tag) {
        return new NutritionError(String.format(FOOD_WITH_A_SPECIFIC_TAG_NOT_FOUND_TEMPLATE, tag));
    }
    public static final String FOOD_NOT_FOUND = "Food with ID not found";

    /// === Inference Errors ===
    public static final String ERROR_COMUNICATING_WITH_INFERENCE_SERVICE = "Error communicating with inference service";
    public static final String FAILED_TO_READ_IMAGE_BYTES = "Failed to read image bytes";

    // === Meal Errors ===
    public static final String MEAL_NOT_FOUND = "Meal not found";

}

