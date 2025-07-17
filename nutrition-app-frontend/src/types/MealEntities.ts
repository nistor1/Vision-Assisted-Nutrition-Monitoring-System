import type {
  FoodItemSimple,
  NutritionCarbohydrates,
  NutritionMinerals,
  NutritionProximates,
  NutritionVitamins
} from "./FoodEntities.ts";

export type MealType = "BREAKFAST" | "LUNCH" | "DINNER" | "SNACK" | "OTHER";
export type MealStatus = "DRAFT" | "FINALIZED";

export interface MealEntry {
  id: string;
  foodItem: FoodItemSimple;
  quantity: number;
}

export interface Meal {
  id: string;
  name: string;
  mealType: MealType;
  mealStatus: MealStatus;
  createdAt: string;
  totalCalories: number;
  totalProteins: number;
  totalCarbohydrates: number;
  totalFats: number;
  totalSugars: number;
  userId: string;
  entries: MealEntry[];
}

export interface CreateMealRequest {
  name: string;
  mealType: MealType;
  entries: {
    foodItemId: string;
    quantity: number;
  }[];
}

export interface UpdateMealRequest {
  id: string;
  name: string;
  mealType: MealType;
  entries: {
    foodItemId: string;
    quantity: number;
  }[];
}

export interface DailyStatisticResponse {
  date: string;
  totalCalories: number;
  totalProteins: number;
  totalFats: number;
  totalSugars: number;
  totalCarbs: number;
  proximates: NutritionProximates;
  minerals: NutritionMinerals;
  carbohydrates: NutritionCarbohydrates;
  vitamins: NutritionVitamins;
}

export interface GoalsStatistics {
  totalCalories: number;
  totalProteins: number;
  totalCarbohydrates: number;
  totalFats: number;
  totalSugars: number;

  carbohydrate: number;
  fiber: number;
  sucrose: number;
  glucose: number;
  fructose: number;
  maltose: number;
  lactose: number;

  calcium: number;
  iron: number;
  magnesium: number;
  phosphorus: number;
  potassium: number;
  sodium: number;
  zinc: number;
  copper: number;
  manganese: number;

  water: number;
  energyGeneral: number;
  energySpecific: number;
  nitrogen: number;
  protein: number;
  totalLipid: number;
  ash: number;

  vitaminA: number;
  vitaminB1: number;
  vitaminB2: number;
  vitaminB3: number;
  vitaminB5: number;
  vitaminB6: number;
  vitaminB7: number;
  vitaminB9: number;
  vitaminB12: number;
  vitaminC: number;
  vitaminD: number;
  vitaminE: number;
  vitaminK: number;
}

