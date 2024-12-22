import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Constant} from "../utils/constant";
import {Observable} from "rxjs";
import {AppResponseWithMessage, AppResponseWithNoData} from "../utils/app.response";
import {EditMessage} from "../models/message";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private  http: HttpClient) { }

  deleteMessage(messageId: string, userId: string): Observable<AppResponseWithNoData> {
    return this.http.delete<AppResponseWithNoData>(`${Constant.MESSAGES_URL}`, {
      params: { messageId, userId},
    });
  }

  editMessage(content: string, userId: string, messageId: string): Observable<AppResponseWithMessage> {
    const editedMessage: EditMessage = {
      content,
      userId
    }
    return this.http.put<AppResponseWithMessage>(`${Constant.MESSAGES_URL}/${messageId}`, editedMessage);
  }
}
