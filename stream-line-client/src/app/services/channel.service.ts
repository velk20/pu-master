import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Constant } from "../utils/constant";
import { Observable } from "rxjs";
import {AppResponse, AppResponseWithMessage, AppResponseWithNoData} from "../utils/app.response";
import {
  FriendMessage,
  NewChannel,
  NewChannelName,
  NewMessage,
  UserToChannel,
  UserFriendMessage
} from "../models/channel";
import {UserRoleToChannel} from "../models/user";

@Injectable({
  providedIn: 'root'
})
export class ChannelService {

  constructor(private http: HttpClient) { }

  getChannelsForUser(userId: string): Observable<AppResponse> {
    return this.http.get<AppResponse>(`${Constant.CHANNELS_URL}/${userId}`);
  }

  addOrRemoveUserFromChannel(userToChannel: UserToChannel): Observable<AppResponse> {
    return this.http.put<AppResponse>(`${Constant.CHANNELS_URL}/users`, userToChannel);
  }

  addMessage(newMessage: NewMessage): Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(`${Constant.CHANNELS_URL}/addMessage`, newMessage);
  }

  createChannel(newChannel: NewChannel):Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(`${Constant.CHANNELS_URL}`, newChannel);
  }

  deleteChannel(id: string):Observable<AppResponseWithNoData> {
    return this.http.delete<AppResponseWithNoData>(`${Constant.CHANNELS_URL}/${id}`);
  }

  renameChannel(updateChannel: NewChannelName):Observable<AppResponseWithMessage> {
    return this.http.put<AppResponseWithMessage>(`${Constant.CHANNELS_URL}`, updateChannel);
  }

  getAvailableUsersForChannel(channelId: string): Observable<AppResponse> {
    return this.http.get<AppResponse>(`${Constant.CHANNELS_URL}/${channelId}/availableUsers`);
  }

  updateUserRoleForChannel(newRoleUser: UserRoleToChannel):Observable<AppResponseWithMessage> {
    return this.http.put<AppResponseWithMessage>(`${Constant.CHANNELS_URL}/userRole`, newRoleUser);
  }
}
