import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";
import {JwtTokenResponse, LoginUser, RegisterUser} from "../models/auth";
import {Constant} from "../utils/constant";
import {AppResponseWithMessage} from "../utils/app.response";
import {User} from "../models/user";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private jwtSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(private http: HttpClient) {
    const token = localStorage.getItem('token');
    this.jwtSubject.next(token ? token : null);
  }

  registerUser(user: RegisterUser): Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(Constant.AUTH_URL+'/register', user);
  }

  loginUser(user: LoginUser): Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(Constant.AUTH_URL+'/authenticate', user);
  }

  login(token: JwtTokenResponse): void {
    localStorage.setItem('token', token.token);
    this.jwtSubject.next(token);
  }

  logout(): void {
    localStorage.removeItem('token');
    this.jwtSubject.next(null);
  }

  getJwtToken(): Observable<string> {
    return this.jwtSubject.asObservable();
  }

  isLoggedIn(): boolean {
    return !!this.jwtSubject.value;
  }
}
