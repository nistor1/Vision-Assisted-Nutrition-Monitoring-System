import type { User, AuthRequest, AuthData, RegisterRequest } from '../types/entities.ts';
import type {
  NutritionResponse,
  NutritionResponseBody,
  PageResponse,
} from '../types/response.ts';

import { API_CONFIG } from '../utils/constants';

class ApiService {
  private async request<T>(
      endpoint: string,
      options: RequestInit = {}
  ): Promise<NutritionResponse<T>> {
    const url = `${API_CONFIG.BASE_URL}${endpoint}`;
    const authUser = localStorage.getItem('authUser');
    const token = authUser ? JSON.parse(authUser).jwtToken : null;

    const headers: Record<string, string> = {
      'Content-Type': 'application/json',
      ...(options.headers as Record<string, string>),
    };

    headers['Authorization'] = `Bearer ${token ?? 'hardcoded-token-aici'}`;

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

  async getUsers(filters :{
    page?: number;
    size?: number;
  }): Promise<NutritionResponse<PageResponse<User>>> {
    console.log('Fetching users...');
    const params = new URLSearchParams();
    params.append('page', (filters.page ?? 0).toString());
    params.append('size', (filters.size ?? 10).toString());
    return this.request<PageResponse<User>>(`/users?${params.toString()}`, { method: 'GET' });
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

}

export const apiService = new ApiService();
