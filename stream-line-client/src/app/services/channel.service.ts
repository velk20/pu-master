import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Constant } from "../utils/constant";
import { Observable } from "rxjs";
import {AppResponse, AppResponseWithMessage, AppResponseWithNoData} from "../utils/app.response";
import {FriendMessage, NewChannel, NewChannelName, NewMessage, UserFriendMessage} from "../models/channel";

@Injectable({
  providedIn: 'root'
})
export class ChannelService {

  constructor(private http: HttpClient) { }

  getChannelsForUser(userId: string): Observable<AppResponse> {

    return this.http.get<AppResponse>(`${Constant.CHANNELS_URL}/${userId}`);
  }

  addMessage(newMessage: NewMessage): Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(`${Constant.CHANNELS_URL}/addMessage`, newMessage);
  }

  sendMessage(friendMessage: FriendMessage): Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(`${Constant.CHANNELS_URL}/sendMessage`, friendMessage)
  }

  createChannel(newChannel: NewChannel):Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(`${Constant.CHANNELS_URL}`, newChannel);
  }

  deleteChannel(id: string):Observable<AppResponseWithNoData> {
    return this.http.delete<AppResponseWithNoData>(`${Constant.CHANNELS_URL}/${id}`);
  }

  getFriendMessages(getMessages: UserFriendMessage):Observable<AppResponse> {
    return this.http.get<AppResponse>(`${Constant.CHANNELS_URL}/${getMessages.userId}/friendMessages/${getMessages.friendId}`);
  }

  renameChannel(updateChannel: NewChannelName):Observable<AppResponseWithMessage> {
    return this.http.put<AppResponseWithMessage>(`${Constant.CHANNELS_URL}`, updateChannel);
  }
}
