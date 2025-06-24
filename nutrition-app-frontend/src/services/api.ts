import type { User } from '../types/entities.ts';
import type {
  NutritionResponse,
  NutritionResponseBody,
} from '../types/response.ts';

import { API_CONFIG } from '../utils/constants';

class ApiService {
  private async request<T>(
    endpoint: string,
    options: RequestInit = {}
  ): Promise<NutritionResponse<T>> {
    const url = `${API_CONFIG.BASE_URL}${endpoint}`;
    const token = localStorage.getItem('token');

    const headers: Record<string, string> = {
      'Content-Type': 'application/json',
      ...(options.headers as Record<string, string>),
    };

    if (token) {
      headers['Authorization'] = `Bearer ${token}`;
    }

    const response = await fetch(url, {
      ...options,
      method: options.method ?? 'GET',
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

  async getUsers(): Promise<NutritionResponse<User[]>> {
    console.log('Fetching users...');
    return this.request<User[]>('/users', { method: 'GET' });
  }
}

export const apiService = new ApiService();
