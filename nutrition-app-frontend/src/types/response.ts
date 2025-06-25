export type NutritionError = {
  message: string;
}

export interface NutritionResponseBody<T = unknown> {
  message: string;
  status: string;
  payload?: T;
  errors?: NutritionError[];
}

export interface NutritionResponse<T = unknown> {
  body: NutritionResponseBody<T>;
  message: string;
  status: string;
  headers: Record<string, string>;
}

export interface PageResponse<T> {
  content: T[];
  page?: number;
  size?: number;
  totalPages?: number;
  totalElements?: number;
}