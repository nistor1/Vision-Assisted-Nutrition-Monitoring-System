import {Route, Routes} from "react-router-dom";
//import NotFoundPage from "../pages/NotFoundPage.tsx";
import RegisterPage from "../pages/RegisterPage";
import HomePage from '../pages/HomePage';
import LoginPage from "../pages/LoginPage.tsx";
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
  NOT_FOUND: "*"
} as const;

export default function AppRouter() {
  return (
  <Routes>
    <Route path={ROUTES.HOME} element={<HomePage/>}/>
    <Route path={ROUTES.INDEX} element={<HomePage/>}/>
    <Route path={ROUTES.LOGIN} element={<LoginPage/>}/>
    <Route path={ROUTES.REGISTER} element={<RegisterPage/>}/>

  </Routes>
);
}