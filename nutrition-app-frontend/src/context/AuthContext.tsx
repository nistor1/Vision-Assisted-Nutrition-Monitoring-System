import { createContext, useContext, useState, type ReactNode } from 'react';
import type { AuthContextType, AuthUser, UserRole } from '../types/entities.ts';

const defaultUser: AuthUser = {
  logged: false,
  username: '',
  role: undefined,
  jwtToken: '',
  id: '',
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [user, setUser] = useState<AuthUser>(() => {
    const savedUser = localStorage.getItem('user');
    return savedUser ? JSON.parse(savedUser) : defaultUser;
  });

  const login = (username: string, roleFromBackend: string, jwtToken: string, id: string) => {
    const role: UserRole =
      roleFromBackend === 'ADMIN' || roleFromBackend === 'USER'
        ? roleFromBackend
        : undefined;

    const loggedUser: AuthUser = { logged: true, username, role, jwtToken, id };
    setUser(loggedUser);
    localStorage.setItem('role', role ?? '');
    localStorage.setItem('user', JSON.stringify(loggedUser));
    localStorage.setItem('jwtToken', jwtToken);
    localStorage.setItem('id', id);
  };

  const logout = () => {
    setUser(defaultUser);
    localStorage.removeItem('role');
    localStorage.removeItem('user');
    localStorage.removeItem('id');
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
