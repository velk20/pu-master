import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {provideToastr} from "ngx-toastr";
import {HTTP_INTERCEPTORS, provideHttpClient} from "@angular/common/http";
import {provideAnimations} from "@angular/platform-browser/animations";
import {JwtInterceptor} from "./auth/jwt-interceptor";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideAnimations(),
    provideHttpClient(),
    provideToastr(),
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},
  ]
};
