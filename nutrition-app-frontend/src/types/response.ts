export type NutritionError = {
  message: string;
}

export interface NutritionResponseBody<T = unknown> {
  message: string;
  status: number;
  payload?: T;
  errors?: NutritionError[];
}

export interface NutritionResponse<T = unknown> {
  body: NutritionResponseBody<T>;
  message: string;
  status: number;
  headers: Record<string, string>;
}