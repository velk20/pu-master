import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";

@Injectable({ providedIn: "root" })
export class JwtInterceptor implements HttpInterceptor {
  token: string | null = null;

  constructor(private authService: AuthService) {
  }
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    this.authService.getJwtToken().subscribe((token)=>{
      this.token = token;
    })

    if (this.token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.token}`,
        },
      });
    }
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Handle 401 Unauthorized
          console.error('Unauthorized request, logging out...');
          localStorage.removeItem('jwtToken');
        }
        return throwError(() => error);
      })
    );
  }
}
