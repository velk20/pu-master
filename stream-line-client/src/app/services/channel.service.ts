import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Constant } from "../utils/constant";
import { Observable } from "rxjs";
import { AppResponse } from "../utils/app.response";

@Injectable({
  providedIn: 'root'
})
export class ChannelService {

  constructor(private http: HttpClient) { }

  getChannelsForUser(userId: string): Observable<AppResponse> {

    return this.http.get<AppResponse>(`${Constant.CHANNELS_URL}/${userId}`);
  }
}
