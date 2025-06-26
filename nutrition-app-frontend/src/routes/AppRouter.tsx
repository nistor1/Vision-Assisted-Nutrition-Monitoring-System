import {Route, Routes} from "react-router-dom";
//import NotFoundPage from "../pages/NotFoundPage.tsx";
import RegisterPage from "../pages/RegisterPage";
import HomePage from '../pages/HomePage';
import LoginPage from "../pages/LoginPage.tsx";
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
//import ProfilePage from "../pages/ProfilePage.tsx";
//import ForgotPasswordPage from "../pages/ForgotPasswordPage.tsx";
//import ResetPasswordPage from "../pages/ResetPasswordPage.tsx";
//import AboutPage from "../pages/AboutPage.tsx";
//import ContactPage from "../pages/ContactPage.tsx";

/*

<Route path={ROUTES.FORGOT_PASSWORD} element={<ForgotPasswordPage/>}/>
<Route path={ROUTES.RESET_PASSWORD} element={<ResetPasswordPage/>}/>

<Route path={ROUTES.HOME} element={<PrivateRoute><HomePage/></PrivateRoute>}/>
<Route path={ROUTES.INDEX} element={<PrivateRoute><HomePage/></PrivateRoute>}/>
<Route path={ROUTES.PROFILE} element={<PrivateRoute><ProfilePage/></PrivateRoute>}/>
<Route path={ROUTES.ABOUT} element={<PrivateRoute><AboutPage/></PrivateRoute>}/>
<Route path={ROUTES.CONTACT} element={<PrivateRoute><ContactPage/></PrivateRoute>}/>


<Route path={ROUTES.NOT_FOUND} element={<NotFoundPage/>}/>
*/

export const ROUTES = {
  INDEX: "/",
  HOME: "/home",
  LOGIN: "/login",
  LOGOUT: "/logout",
  REGISTER: "/register",
  FORGOT_PASSWORD: "/forgot-password",
  RESET_PASSWORD: "/reset-password",
  PROFILE: "/profile/:username",
  ABOUT: "/about",
  CONTACT: "/contact",
  ADMIN_ROUTE: "/admin",
  USERS: "/users",
  USER_PROFILE: "/users/:id",
  USER_PERSONAL_PROFILE: "/users/personal",
  USER_CREATE: "/users/new",
  USER_UPDATE: "/users/edit/:id",
  FOODITEMS: "/food-items",
  FOODITEMS_CREATE: "/food-items/new",
  FOODITEMS_UPDATE: "/food-items/edit/:id",
  FOODITEMS_VIEW: "/food-items/:id",
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
    <Route path={ROUTES.USER_PERSONAL_PROFILE} element={<UserPersonalProfilePage />} />



  </Routes>
);
}