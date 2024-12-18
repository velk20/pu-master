import { Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {RegisterComponent} from "./components/register/register.component";
import {LoginComponent} from "./components/login/login.component";
import {AboutComponent} from "./components/about/about.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {authGuard} from "./auth/auth.guard";
import {NotfoundComponent} from "./components/notfound/notfound.component";
import {homeGuard} from "./auth/home.guard";
import {ProfileComponent} from "./components/profile/profile.component";

export const routes: Routes = [
  {path: '', component: HomeComponent, canActivate: [homeGuard]},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'about', component: AboutComponent},
  {path: 'dashboard', component: DashboardComponent, canActivate: [authGuard]},
  {path: '**', component: NotfoundComponent}
];
