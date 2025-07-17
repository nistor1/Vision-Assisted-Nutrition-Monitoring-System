import type {
  User,
  AuthRequest,
  AuthData,
  RegisterRequest,
  UserDetails,
  UpdateProfileRequest
} from '../types/UserEntities.ts';
import type {
  NutritionResponse,
  NutritionResponseBody,
  PageResponse,
} from '../types/response.ts';

import { API_CONFIG } from '../utils/constants';
import type {FoodItem, FoodItemSimple} from "../types/FoodEntities.ts";
import type {
  CreateMealRequest,
  DailyStatisticResponse,
  GoalsStatistics,
  Meal,
  UpdateMealRequest
} from "../types/MealEntities.ts";

class ApiService {
  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<NutritionResponse<T>> {
    const url = `${API_CONFIG.BASE_URL}${endpoint}`;
    const authUser = localStorage.getItem('authUser');
    const token = authUser ? JSON.parse(authUser).jwtToken : null;

    const headers: Record<string, string> = {
      ...(options.headers as Record<string, string>),
    };

    if (!(options.body instanceof FormData)) {
      headers['Content-Type'] = 'application/json';
    }

    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    const response = await fetch(url, {
      ...options,
      headers,
    });

    const rawHeaders: Record<string, string> = {};
    response.headers.forEach((value, key) => {
      rawHeaders[key] = value;
    });

    const isJson = response.headers
      .get('content-type')
      ?.includes('application/json');

    const body: NutritionResponseBody<T> = isJson
      ? await response.json()
      : {
        message: response.statusText,
        status: response.status,
      };

    return {
      body,
      message: body.message,
      status: body.status,
      headers: rawHeaders,
    };
  }

  // === USERS ===

  async getUsers(filters: {
    page?: number;
    size?: number;
    role?: string;
  }): Promise<NutritionResponse<PageResponse<User>>> {
    console.log('Fetching users...');
    const params = new URLSearchParams();
    params.append('page', (filters.page ?? 0).toString());
    params.append('size', (filters.size ?? 5).toString());
    if (filters.role) {
      params.append('search', filters.role);
    }
    return this.request<PageResponse<User>>(`/users?${params.toString()}`, {method: 'GET'});
  }

  async deleteUser(id: string): Promise<NutritionResponse<void>> {
    if (!id?.trim()) {
      throw new Error('Invalid user ID');
    }

    return this.request<void>(`/users/${id}`, {
      method: 'DELETE',
    });
  }

  async getUserById(id: string): Promise<NutritionResponse<UserDetails>> {
    if (!id?.trim()) {
      throw new Error('Invalid user ID');
    }

    return this.request<UserDetails>(`/users/${id}`, {
      method: 'GET',
    });
  }

  async getUserPersonal(): Promise<NutritionResponse<UserDetails>> {
    return this.request<UserDetails>(`/users/personal`, {
      method: 'GET',
    });
  }

  async createUser(user: Omit<UserDetails, 'id'>): Promise<NutritionResponse<UserDetails>> {
    return this.request<UserDetails>('/users', {
      method: 'POST',
      body: JSON.stringify(user),
    });
  }

  async updateUser(data: UserDetails): Promise<NutritionResponse<UserDetails>> {
    return this.request<UserDetails>(`/users`, {
      method: 'PATCH',
      body: JSON.stringify(data),
    });
  }

  async updateUserProfile(data: UpdateProfileRequest): Promise<NutritionResponse<UserDetails>> {
    return this.request<UserDetails>(`/users/personal`, {
      method: 'PATCH',
      body: JSON.stringify(data),
    });
  }

  async login(credentials: AuthRequest): Promise<AuthData> {
    const response = await this.request<AuthData>('/auth/login', {
      method: 'POST',
      body: JSON.stringify(credentials),
    });

    if (response.body.status !== 'OK' || !response.body.payload) {
      throw new Error(response.body.message || 'Login failed');
    }

    return response.body.payload;
  }

  async register(data: RegisterRequest): Promise<AuthData> {
    const response = await this.request<AuthData>('/auth/register', {
      method: 'POST',
      body: JSON.stringify(data),
    });

    if (response.body.status !== 'OK' || !response.body.payload) {
      throw new Error(response.body.message || 'Registration failed');
    }

    return response.body.payload;
  }

  async resetPassword(token: string, newPassword: string): Promise<NutritionResponse<null>> {
    if (!token || !newPassword) {
      throw new Error('Invalid reset token or password');
    }

    return await this.request<null>(`/auth/reset-password`, {
      method: 'POST',
      body: JSON.stringify({
        token,
        newPassword
      }),
    });
  }

  async forgotPassword(email: string): Promise<NutritionResponse<null>> {
    if (!email?.trim()) {
      throw new Error('Invalid email address');
    }

    return await this.request<null>(`/auth/forgot-password`, {
      method: 'POST',
      body: JSON.stringify({ email }),
    });
  }

  // === FOOD ITEMS ===

  async getFoodItems(filters: {
    page?: number;
    size?: number;
    category?: string;
  }): Promise<NutritionResponse<PageResponse<FoodItemSimple>>> {
    const params = new URLSearchParams();
    params.append('page', (filters.page ?? 0).toString());
    params.append('size', (filters.size ?? 10).toString());
    if (filters.category) {
      params.append('search', filters.category);
    }
    return this.request(`/food-items?${params.toString()}`, {method: 'GET'});
  }

  async getFoodItemsSimple(filters: {
    page?: number;
    size?: number;
    category?: string;
  }): Promise<NutritionResponse<PageResponse<FoodItemSimple>>> {
    const params = new URLSearchParams();
    params.append('page', (filters.page ?? 0).toString());
    params.append('size', (filters.size ?? 10).toString());
    if (filters.category) {
      params.append('search', filters.category);
    }

    return this.request(`/food-items/simple?${params.toString()}`, {method: 'GET'});
  }

  async getFoodItemById(id: string): Promise<NutritionResponse<FoodItem>> {
    if (!id?.trim()) {
      throw new Error('Invalid food item ID');
    }

    return this.request<FoodItem>(`/food-items/${id}`, {
      method: 'GET',
    });
  }

  async deleteFoodItem(id: string): Promise<NutritionResponse<void>> {
    if (!id?.trim()) {
      throw new Error('Invalid food item ID');
    }

    return this.request<void>(`/food-items/${id}`, {
      method: 'DELETE',
    });
  }

  async createFoodItem(data: Omit<FoodItem, 'id'>): Promise<NutritionResponse<FoodItem>> {
    return this.request<FoodItem>('/food-items', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

  async updateFoodItem(data: FoodItem): Promise<NutritionResponse<FoodItem>> {
    return this.request<FoodItem>(`/food-items`, {
      method: 'PATCH',
      body: JSON.stringify(data),
    });
  }

  // === MEALS ===

  async getMealsForUser(filters: {
    page?: number;
    size?: number;
    date?: string;
  }): Promise<NutritionResponse<PageResponse<Meal>>> {
    const params = new URLSearchParams();
    params.append('page', (filters.page ?? 0).toString());
    params.append('size', (filters.size ?? 10).toString());
    if (filters.date) {
      params.append('date', filters.date);
    }
    return this.request(`/meals?${params.toString()}`, {method: 'GET'});
  }

  async getMealById(id: string): Promise<NutritionResponse<Meal>> {
    if (!id?.trim()) {
      throw new Error('Invalid meal ID');
    }

    return this.request<Meal>(`/meals/${id}`, {
      method: 'GET',
    });
  }

  async deleteMeal(id: string): Promise<NutritionResponse<null>> {
    if (!id?.trim()) {
      throw new Error('Invalid meal ID');
    }

    return this.request<null>(`/meals/${id}`, {
      method: 'DELETE',
    });
  }

  async createMeal(data: CreateMealRequest): Promise<NutritionResponse<Meal>> {
    return this.request<Meal>('/meals/request', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  }

  async createMealFromPhoto(formData: FormData): Promise<NutritionResponse<Meal>> {
    return this.request<Meal>('/meals', {
      method: 'POST',
      body: formData,
    });
  }

  async updateMeal(data: UpdateMealRequest): Promise<NutritionResponse<Meal>> {
    return this.request<Meal>(`/meals`, {
      method: 'PATCH',
      body: JSON.stringify(data),
    });
  }

  async finalizeMeal(id: string): Promise<NutritionResponse<Meal>> {
    return this.request<Meal>(`/meals/${id}/finalize`, {
      method: 'PATCH',
    });
  }

  async getDailyStatistics(date: string): Promise<NutritionResponse<DailyStatisticResponse>> {
    if (!date?.trim()) {
      throw new Error('Invalid date');
    }

    return this.request<DailyStatisticResponse>(`/meals/statistics/day?date=${date}`, {
      method: 'GET',
    });
  }

  async getWeeklyStatistics(): Promise<NutritionResponse<DailyStatisticResponse[]>> {
    return this.request<DailyStatisticResponse[]>(`/meals/statistics/week`, {
      method: 'GET',
    });
  }

  async getGoals(): Promise<NutritionResponse<GoalsStatistics>> {
    return this.request<GoalsStatistics>(`/goals/user`, {
      method: 'GET',
    });
  }

  async updateGoals(data: GoalsStatistics): Promise<NutritionResponse<GoalsStatistics>> {
    return this.request<GoalsStatistics>(`/goals`, {
      method: 'PATCH',
      body: JSON.stringify(data),
    });
  }

}


export const apiService = new ApiService();
