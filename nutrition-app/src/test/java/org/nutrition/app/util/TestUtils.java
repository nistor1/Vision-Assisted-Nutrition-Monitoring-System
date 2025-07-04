package org.nutrition.app.util;

import org.nutrition.app.food.dto.FoodItemDTO;
import org.nutrition.app.food.dto.request.create.CreateFoodItemRequest;
import org.nutrition.app.food.dto.request.create.CreateNutritionCarbohydratesRequest;
import org.nutrition.app.food.dto.request.create.CreateNutritionMineralsRequest;
import org.nutrition.app.food.dto.request.create.CreateNutritionProximatesRequest;
import org.nutrition.app.food.dto.request.create.CreateNutritionVitaminsRequest;
import org.nutrition.app.food.dto.request.update.UpdateFoodItemRequest;
import org.nutrition.app.food.entity.FoodItem;
import org.nutrition.app.food.entity.NutritionCarbohydrates;
import org.nutrition.app.food.entity.NutritionMinerals;
import org.nutrition.app.food.entity.NutritionProximates;
import org.nutrition.app.food.entity.NutritionVitamins;
import org.nutrition.app.goals.entity.Goal;
import org.nutrition.app.meal.constants.MealStatus;
import org.nutrition.app.meal.constants.MealType;
import org.nutrition.app.meal.entity.Meal;
import org.nutrition.app.meal.entity.MealEntry;
import org.nutrition.app.user.dto.UserDTO;
import org.nutrition.app.user.dto.request.CreateUserRequest;
import org.nutrition.app.user.dto.request.UpdateUserRequest;
import org.nutrition.app.user.entity.User;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.math.BigDecimal.valueOf;
import static org.nutrition.app.util.TestUtils.UserUtils.randomUser;

public class TestUtils {

    public static String randomString() {
        return UUID.randomUUID().toString().replaceAll("_", "");
    }

    public interface UserUtils {
        static User randomUser() {
            return new User(
                    UUID.randomUUID(),
                    randomString(),
                    randomString() + "@example.com",
                    Timestamp.from(Instant.now()),
                    randomString(),
                    Role.USER,
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString()
            );
        }

        static UserDTO randomUserDTO() {
            return Mapper.mapTo(randomUser(), UserDTO.class);
        }

        static UserDTO randomUserDTO(final String email) {
            var user = randomUser();
            user.setEmail(email);
            return Mapper.mapTo(user, UserDTO.class);
        }

        static CreateUserRequest createUserRequest(final User user) {
            return new CreateUserRequest(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getRole(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getCity(),
                    user.getPostalCode(),
                    user.getCountry()
            );
        }

        static UpdateUserRequest updateUserRequest(final User user) {
            return new UpdateUserRequest(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole(),
                    user.getFullName(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getCity(),
                    user.getPostalCode(),
                    user.getCountry()
            );
        }

    }

    public interface FoodUtils {

        static FoodItem randomFoodItem() {
            return FoodItem.builder()
                    .withId(UUID.randomUUID())
                    .withTag((int) (Math.random() * 1000))
                    .withFoodName(randomString())
                    .withCategory("Fruits")
                    .withImageUrl("https://example.com/image.jpg")
                    .withServingSize(100.0)
                    .withServingUnit("g")
                    .withCreatedAt(new Date())
                    .withUpdatedAt(new Date())
                    .withActive(true)
                    .withProximates(randomProximates())
                    .withMinerals(randomMinerals())
                    .withCarbohydrates(randomCarbohydrates())
                    .withVitamins(randomVitamins())
                    .build();
        }

        static FoodItemDTO randomFoodItemDTO() {
            return Mapper.mapTo(randomFoodItem(), FoodItemDTO.class);
        }

        static CreateNutritionProximatesRequest mapToProximatesRequest(NutritionProximates p) {
            return CreateNutritionProximatesRequest.builder()
                    .withPortionSize(p.getPortionSize())
                    .withUnit(p.getUnit())
                    .withWater(p.getWater())
                    .withEnergyGeneral(p.getEnergyGeneral())
                    .withEnergySpecific(p.getEnergySpecific())
                    .withNitrogen(p.getNitrogen())
                    .withProtein(p.getProtein())
                    .withTotalLipid(p.getTotalLipid())
                    .withAsh(p.getAsh())
                    .build();
        }

        static CreateNutritionMineralsRequest mapToMineralsRequest(NutritionMinerals m) {
            return CreateNutritionMineralsRequest.builder()
                    .withPortionSize(m.getPortionSize())
                    .withUnit(m.getUnit())
                    .withCalcium(m.getCalcium())
                    .withIron(m.getIron())
                    .withMagnesium(m.getMagnesium())
                    .withPhosphorus(m.getPhosphorus())
                    .withPotassium(m.getPotassium())
                    .withSodium(m.getSodium())
                    .withZinc(m.getZinc())
                    .withCopper(m.getCopper())
                    .withManganese(m.getManganese())
                    .build();
        }

        static CreateNutritionCarbohydratesRequest mapToCarbohydratesRequest(NutritionCarbohydrates c) {
            return CreateNutritionCarbohydratesRequest.builder()
                    .withPortionSize(c.getPortionSize())
                    .withUnit(c.getUnit())
                    .withCarbohydrate(c.getCarbohydrate())
                    .withFiber(c.getFiber())
                    .withTotalSugars(c.getTotalSugars())
                    .withSucrose(c.getSucrose())
                    .withGlucose(c.getGlucose())
                    .withFructose(c.getFructose())
                    .withMaltose(c.getMaltose())
                    .withLactose(c.getLactose())
                    .build();
        }

        static CreateNutritionVitaminsRequest mapToVitaminsRequest(NutritionVitamins v) {
            return CreateNutritionVitaminsRequest.builder()
                    .withPortionSize(v.getPortionSize())
                    .withUnit(v.getUnit())
                    .withVitaminA(v.getVitaminA())
                    .withVitaminB1(v.getVitaminB1())
                    .withVitaminB2(v.getVitaminB2())
                    .withVitaminB3(v.getVitaminB3())
                    .withVitaminB5(v.getVitaminB5())
                    .withVitaminB6(v.getVitaminB6())
                    .withVitaminB7(v.getVitaminB7())
                    .withVitaminB9(v.getVitaminB9())
                    .withVitaminB12(v.getVitaminB12())
                    .withVitaminC(v.getVitaminC())
                    .withVitaminD(v.getVitaminD())
                    .withVitaminE(v.getVitaminE())
                    .withVitaminK(v.getVitaminK())
                    .build();
        }

        static CreateFoodItemRequest createFoodItemRequest(FoodItem foodItem) {
            return new CreateFoodItemRequest(
                    foodItem.getTag(),
                    foodItem.getFoodName(),
                    foodItem.getCategory(),
                    foodItem.getImageUrl(),
                    foodItem.getServingSize(),
                    foodItem.getServingUnit(),
                    foodItem.isActive(),
                    mapToProximatesRequest(foodItem.getProximates()),
                    mapToMineralsRequest(foodItem.getMinerals()),
                    mapToCarbohydratesRequest(foodItem.getCarbohydrates()),
                    mapToVitaminsRequest(foodItem.getVitamins())
            );
        }


        static UpdateFoodItemRequest updateFoodItemRequest(FoodItem foodItem) {
            return new UpdateFoodItemRequest(
                    foodItem.getId(),
                    foodItem.getTag(),
                    foodItem.getFoodName(),
                    foodItem.getCategory(),
                    foodItem.getImageUrl(),
                    foodItem.getServingSize(),
                    foodItem.getServingUnit(),
                    foodItem.isActive(),
                    null,
                    null,
                    null,
                    null
            );
        }

        static NutritionProximates randomProximates() {
            return NutritionProximates.builder()
                    .withPortionSize(100.0)
                    .withEnergyGeneral(140.0)
                    .withEnergySpecific(114.0)
                    .withProtein(1.5)
                    .withNitrogen(0.5)
                    .withAsh(0.2)
                    .withWater(80.0)
                    .build();
        }

        static NutritionMinerals randomMinerals() {
            return NutritionMinerals.builder()
                    .withPortionSize(100.0)
                    .withCalcium(50.0)
                    .withIron(2.0)
                    .withMagnesium(10.0)
                    .withPhosphorus(20.0)
                    .withPotassium(100.0)
                    .withSodium(5.0)
                    .withZinc(0.8)
                    .build();
        }

        static NutritionCarbohydrates randomCarbohydrates() {
            return NutritionCarbohydrates.builder()
                    .withPortionSize(100.0)
                    .withCarbohydrate(25.0)
                    .withTotalSugars(10.0)
                    .withFiber(2.0)
                    .withFructose(3.0)
                    .build();
        }

        static NutritionVitamins randomVitamins() {
            return NutritionVitamins.builder()
                    .withPortionSize(100.0)
                    .withVitaminA(30.0)
                    .withVitaminB1(0.1)
                    .withVitaminB2(0.1)
                    .withVitaminB3(0.3)
                    .withVitaminB6(0.2)
                    .withVitaminB7(15.0)
                    .withVitaminB9(200.0)
                    .withVitaminE(1.5)
                    .withVitaminK(5.0)
                    .withVitaminD(0.0)
                    .build();
        }
    }

    public interface GoalUtils {

        static Goal randomGoal() {
            return randomGoal(randomUser());
        }

        static Goal randomGoal(User user) {
            return Goal.builder()
                    .withId(UUID.randomUUID())
                    .withUser(user)
                    .withTotalCalories(valueOf(2200))
                    .withTotalProteins(valueOf(120))
                    .withTotalCarbohydrates(valueOf(280))
                    .withTotalFats(valueOf(80))
                    .withTotalSugars(valueOf(30))
                    .withCarbohydrate(250.0)
                    .withFiber(25.0)
                    .withSucrose(10.0)
                    .withGlucose(8.0)
                    .withFructose(7.0)
                    .withMaltose(1.0)
                    .withLactose(2.0)
                    .withCalcium(1000.0)
                    .withIron(18.0)
                    .withMagnesium(400.0)
                    .withPhosphorus(700.0)
                    .withPotassium(3500.0)
                    .withSodium(2300.0)
                    .withZinc(11.0)
                    .withCopper(0.9)
                    .withManganese(2.3)
                    .withWater(2000.0)
                    .withEnergyGeneral(200.0)
                    .withEnergySpecific(500.0)
                    .withNitrogen(5.0)
                    .withProtein(100.0)
                    .withTotalLipid(70.0)
                    .withAsh(8.0)
                    .withVitaminA(900.0)
                    .withVitaminB1(1.2)
                    .withVitaminB2(1.3)
                    .withVitaminB3(16.0)
                    .withVitaminB5(5.0)
                    .withVitaminB6(1.7)
                    .withVitaminB7(30.0)
                    .withVitaminB9(400.0)
                    .withVitaminB12(2.4)
                    .withVitaminC(90.0)
                    .withVitaminD(15.0)
                    .withVitaminE(15.0)
                    .withVitaminK(120.0)
                    .build();
        }
    }

    public interface MealUtils {

        static Meal randomMeal() {
            Meal meal = Meal.builder()
                    .withId(UUID.randomUUID())
                    .withName("Meal " + randomString())
                    .withMealType(MealType.LUNCH)
                    .withMealStatus(MealStatus.FINALIZED)
                    .withCreatedAt(new Date())
                    .withTotalCalories(new BigDecimal("500"))
                    .withTotalProteins(new BigDecimal("30"))
                    .withTotalCarbohydrates(new BigDecimal("60"))
                    .withTotalFats(new BigDecimal("15"))
                    .withTotalSugars(new BigDecimal("10"))
                    .withUserId(UUID.randomUUID())
                    .build();

            MealEntry entry1 = randomMealEntry(meal);
            MealEntry entry2 = randomMealEntry(meal);
            meal.setEntries(new ArrayList<>(List.of(entry1, entry2)));

            return meal;
        }

        static MealEntry randomMealEntry(Meal meal) {
            return MealEntry.builder()
                    .withId(UUID.randomUUID())
                    .withMeal(meal)
                    .withFoodItem(FoodUtils.randomFoodItem())
                    .withQuantity(Math.random() * 300)
                    .build();
        }

        static MealEntry randomMealEntry() {
            Meal meal = Meal.builder()
                    .withId(UUID.randomUUID())
                    .withName("GeneratedMeal")
                    .withMealType(MealType.DINNER)
                    .withMealStatus(MealStatus.DRAFT)
                    .withCreatedAt(new Date())
                    .withUserId(UUID.randomUUID())
                    .build();

            return randomMealEntry(meal);
        }
    }

}
