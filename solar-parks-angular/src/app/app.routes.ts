import { Routes } from '@angular/router';
import {CustomerPage} from "./features/customers/customer.component";
import {ProjectPage} from "./features/projects/project.component";

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'customers',
    pathMatch: 'full'
  },
  {
    path: 'customers',
    component: CustomerPage
  },
  {
    path: 'projects',
    component: ProjectPage
  }
];
