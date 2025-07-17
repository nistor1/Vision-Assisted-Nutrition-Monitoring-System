import { createContext, useContext, useState, type ReactNode } from 'react';
import type { AuthContextType, AuthUser, AuthData } from '../types/UserEntities.ts';

const defaultUser: AuthUser = {
  logged: false,
  username: '',
  role: 'USER',
  jwtToken: '',
  id: '',
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [user, setUser] = useState<AuthUser>(() => {
    const savedUser = localStorage.getItem('authUser');
    return savedUser ? JSON.parse(savedUser) : defaultUser;
  });

  const isAuthenticated = user.logged && user.jwtToken !== '';

  const login = ({ accessToken, user }: AuthData) => {
    const loggedUser: AuthUser = {
      logged: true,
      username: user.username,
      role: user.role,
      jwtToken: accessToken,
      id: user.id,
    };

    setUser(loggedUser);

    localStorage.setItem('authUser', JSON.stringify(loggedUser));
  };

  const logout = () => {
    setUser(defaultUser);
    localStorage.removeItem('authUser');
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, isAuthenticated }}>
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
