import {Route, Routes} from "react-router-dom";
import RegisterPage from "../pages/auth/RegisterPage.tsx";
import HomePage from '../pages/HomePage.tsx';
import LoginPage from "../pages/auth/LoginPage.tsx";
import UsersPage from "../pages/user/UsersPage.tsx";
import UserProfilePage from "../pages/user/UsersProfilePage.tsx";
import CreateUserPage from "../pages/user/CreateUserPage.tsx";
import UpdateUserPage from "../pages/user/UpdateUserPage.tsx";
import AdminRoute from './AdminRoute';
import FoodPage from "../pages/food/FoodPage.tsx";
import FoodItemViewPage from "../pages/food/FoodViewPage.tsx";
import CreateFoodItemPage from "../pages/food/CreateFoodItemPage.tsx";
import UpdateFoodItemPage from "../pages/food/UpdateFoodItemPage.tsx";
import UserPersonalProfilePage from "../pages/user/UserPersonalProfilePage.tsx";
import MealsPage from "../pages/meal/MealsPage.tsx";
import MealViewPage from "../pages/meal/MealViewPage.tsx";
import CreateMealPage from "../pages/meal/CreateMealPage.tsx";
import UpdateMealPage from "../pages/meal/UpdateMealPage.tsx";
import CreateMealFromPhotoPage from "../pages/meal/CreateMealFromPhotoPage.tsx";
import MealStatisticsPage from "../pages/meal/MealStatisticsPage.tsx";
import UpdateGoalPage from "../pages/meal/UpdateGoalPage.tsx";
import ResetPassword from "../pages/auth/ResetPassword.tsx";
import ForgotPassword from "../pages/auth/ForgotPassword.tsx";
import PageNotFound from "../pages/auth/PageNotFound.tsx";
import PrivateRoute from "./PrivateRoute.tsx";
import UserPersonalEditPage from "../pages/user/UserPersonalEditPage.tsx";

export const ROUTES = {
  INDEX: "/",
  HOME: "/home",
  LOGIN: "/login",
  LOGOUT: "/logout",
  REGISTER: "/register",
  FORGOT_PASSWORD: "/forgot-password",
  RESET_PASSWORD: "/reset-password",
  ADMIN_ROUTE: "/admin",
  USERS: "/users",
  USER_PROFILE: "/users/:id",
  USER_PERSONAL_PROFILE: "/users/personal",
  USER_EDIT_PERSONAL_PROFILE: "/users/personal/edit",
  USER_CREATE: "/users/new",
  USER_UPDATE: "/users/edit/:id",
  FOODITEMS: "/food-items",
  FOODITEMS_CREATE: "/food-items/new",
  FOODITEMS_UPDATE: "/food-items/edit/:id",
  FOODITEMS_VIEW: "/food-items/:id",
  MEALS: "/meals",
  MEALS_CREATE_REQUEST: "/meals/new/request",
  MEALS_CREATE: "/meals/new",
  MEALS_UPDATE: "/meals/edit/:id",
  MEALS_VIEW: "/meals/:id",
  MEALS_STATISTICS: "/meals/statistics",
  GOALS_UPDATE: "/goals/edit",
  NOT_FOUND: "*"
} as const;

export default function AppRouter() {
  return (
  <Routes>
    <Route path={ROUTES.HOME} element={<HomePage/>}/>
    <Route path={ROUTES.INDEX} element={<HomePage/>}/>
    <Route path={ROUTES.LOGIN} element={<LoginPage/>}/>
    <Route path={ROUTES.ADMIN_ROUTE + ROUTES.USERS} element={<AdminRoute><UsersPage /></AdminRoute>} />
    <Route path={ROUTES.REGISTER} element={<RegisterPage/>}/>
    <Route path={ROUTES.ADMIN_ROUTE + ROUTES.USER_PROFILE} element={<AdminRoute><UserProfilePage /></AdminRoute>} />
    <Route path={ROUTES.ADMIN_ROUTE + ROUTES.USER_CREATE} element={<AdminRoute><CreateUserPage /></AdminRoute>} />
    <Route path={ROUTES.ADMIN_ROUTE + ROUTES.USER_UPDATE} element={<AdminRoute><UpdateUserPage /></AdminRoute>} />
    <Route path={ROUTES.FOODITEMS} element={<FoodPage />} />
    <Route path={ROUTES.ADMIN_ROUTE + ROUTES.FOODITEMS_CREATE} element={<AdminRoute><CreateFoodItemPage /></AdminRoute>} />
    <Route path={ROUTES.ADMIN_ROUTE + ROUTES.FOODITEMS_UPDATE} element={<AdminRoute><UpdateFoodItemPage /></AdminRoute>} />
    <Route path={ROUTES.FOODITEMS_VIEW} element={<FoodItemViewPage />} />
    <Route path={ROUTES.USER_PERSONAL_PROFILE} element={<PrivateRoute><UserPersonalProfilePage /></PrivateRoute>} />
    <Route path={ROUTES.USER_EDIT_PERSONAL_PROFILE} element={<PrivateRoute><UserPersonalEditPage /></PrivateRoute>} />
    <Route path={ROUTES.MEALS} element={<PrivateRoute><MealsPage/></PrivateRoute>} />
    <Route path={ROUTES.MEALS_VIEW} element={<PrivateRoute><MealViewPage/></PrivateRoute>} />
    <Route path={ROUTES.MEALS_CREATE_REQUEST} element={<PrivateRoute><CreateMealPage/></PrivateRoute>} />
    <Route path={ROUTES.MEALS_UPDATE} element={<PrivateRoute><UpdateMealPage/></PrivateRoute>} />
    <Route path={ROUTES.MEALS_CREATE} element={<PrivateRoute><CreateMealFromPhotoPage/></PrivateRoute>} />
    <Route path={ROUTES.MEALS_STATISTICS} element={<PrivateRoute><MealStatisticsPage/></PrivateRoute>} />
    <Route path={ROUTES.GOALS_UPDATE} element={<PrivateRoute><UpdateGoalPage /></PrivateRoute>} />
    <Route path={ROUTES.FORGOT_PASSWORD} element={<ForgotPassword />} />
    <Route path={ROUTES.RESET_PASSWORD} element={<ResetPassword />} />
    <Route path={ROUTES.NOT_FOUND} element={<PageNotFound />} />

  </Routes>
);
}