
export interface FoodItemSimple {
  id: string;
  foodName: string;
  category: string;
  imageUrl: string;
  servingUnit: string;
  servingSize: number | null;
  active: boolean;
}

export interface FoodItem extends FoodItemSimple{
  tag: number | null;
  createdAt: string;
  updatedAt: string;
  proximates: NutritionProximates;
  minerals: NutritionMinerals;
  carbohydrates: NutritionCarbohydrates;
  vitamins: NutritionVitamins;
}

export interface NutritionCarbohydrates {
  id: string;
  portionSize: number;
  unit: string;
  carbohydrate: number;
  fiber: number;
  totalSugars: number;
  sucrose: number;
  glucose: number;
  fructose: number;
  maltose: number;
  lactose: number;
}

export interface NutritionMinerals {
  id: string;
  portionSize: number;
  unit: string;
  calcium: number;
  iron: number;
  magnesium: number;
  phosphorus: number;
  potassium: number;
  sodium: number;
  zinc: number;
  copper: number;
  manganese: number;
}

export interface NutritionProximates {
  id: string;
  portionSize: number;
  unit: string;
  water: number;
  energyGeneral: number;
  energySpecific: number;
  nitrogen: number;
  protein: number;
  totalLipid: number;
  ash: number;
}

export interface NutritionVitamins {
  id: string;
  portionSize: number;
  unit: string;
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
