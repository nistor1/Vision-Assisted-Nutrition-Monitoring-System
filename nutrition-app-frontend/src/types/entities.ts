export interface PageResponse<T> {
  content: T[];
  totalPages?: number;
  totalElements?: number;
}

export type UserRole = "USER" | "ADMIN"| undefined;

export interface BaseEntity {
  id: string;
}

export interface User {
  id: string;
  username: string;
  role: UserRole;
  email: string;
  createdAt: string;
}

export interface  UserDetails extends User {
  fullName: string;
  address: string;
  city: string;
  country: string;
  postalCode: string;
  phoneNumber: string;
}

export interface AuthUser {
  logged: boolean;
  jwtToken: string;
  username: string;
  role: UserRole;
  id: string;
}

export interface AuthContextType {
  user: AuthUser;
  login: (auth: AuthData) => void;
  logout: () => void;
  isAuthenticated: boolean;
}

export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthData {
  accessToken: string;
  user: User;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}
