import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Constant } from "../utils/constant";
import { Observable } from "rxjs";
import {AppResponse, AppResponseWithMessage} from "../utils/app.response";
import {NewChannel, NewMessage} from "../models/channel";

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

  createChannel(newChannel: NewChannel):Observable<AppResponseWithMessage> {
    return this.http.post<AppResponseWithMessage>(`${Constant.CHANNELS_URL}`, newChannel);
  }
}
