import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AppResponse, AppResponseWithMessage, AppResponseWithNoData} from "../utils/app.response";
import {Constant} from "../utils/constant";
import {AddFriend} from "../models/user";
import {FriendMessage, UserFriendMessage} from "../models/channel";

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

  removeFriend(removeFriend: AddFriend): Observable<AppResponseWithMessage> {
    return this.http.put<AppResponseWithMessage>(Constant.USERS_URL + `/removeFriend`, removeFriend);
  }

  sendMessage(friendMessage: FriendMessage): Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(`${Constant.USERS_URL}/sendMessage`, friendMessage)
  }

  getFriendMessages(getMessages: UserFriendMessage):Observable<AppResponse> {
    return this.http.get<AppResponse>(`${Constant.USERS_URL}/${getMessages.userId}/friendMessages/${getMessages.friendId}`);
  }

  deleteUser(userId: string): Observable<AppResponseWithNoData> {
    return this.http.delete<AppResponseWithNoData>(`${Constant.USERS_URL}/${userId}`);
  }
}
