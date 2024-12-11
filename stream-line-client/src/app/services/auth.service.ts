import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {LoginUser, RegisterUser} from "../models/auth";
import {Constant} from "../utils/constant";
import {AppResponseWithMessage} from "../utils/app.response";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  registerUser(user: RegisterUser): Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(Constant.AUTH_URL+'/register', user);
  }

  loginUser(user: LoginUser): Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(Constant.AUTH_URL+'/authenticate', user);
  }
}
