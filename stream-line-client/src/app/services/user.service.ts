import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AppResponse, AppResponseWithMessage} from "../utils/app.response";
import {Constant} from "../utils/constant";
import {AddFriend} from "../models/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUserById(id: string): Observable<AppResponse> {
    return this.http.get<AppResponse>(Constant.USERS_URL + `/${id}`);
  }

  getAllAvailableUserFriends(userId:string):Observable<AppResponse> {
    return this.http.get<AppResponse>(Constant.USERS_URL+`/${userId}/available-friends`);
  }

  addFriend(addFriend: AddFriend): Observable<AppResponseWithMessage> {
    return this.http.put<AppResponseWithMessage>(Constant.USERS_URL + `/addFriend`, addFriend);
  }
}
