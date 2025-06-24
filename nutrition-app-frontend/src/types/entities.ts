export interface PageResponse<T> {
  content: T[];
  totalPages?: number;
  totalElements?: number;
}

export type UserRole = "USER" | "ADMIN"| undefined;

export interface BaseEntity {
  id: string;
}

export interface  User {
  username: string;
  role: UserRole;
  email: string;
  createdAt: string;
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
  login: (username: string, roleFromBackend: string, jwtToken: string, id: string) => void;
  logout: () => void;
}