import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Constants} from "../util/constants";
import {Observable} from "rxjs";
import {Contact} from "../models/contact";

@Injectable({
  providedIn: 'root'
})
export class ContactService {
  private httpClient = inject(HttpClient);
  private contactUrl = Constants.SERVER_HOSTNAME + "/contacts";

  getAll():Observable<any> {
    return this.httpClient.get<Contact[]>(this.contactUrl);
  }

  getAllByProjectId(projectId: number): Observable<any> {
    return this.httpClient.get<Contact[]>(`${this.contactUrl}/project/${projectId}`);
  }

  getById(id:number): Observable<any> {
    return this.httpClient.get<Contact>(`${this.contactUrl}/${id}`);
  }

  create(contact:Contact): Observable<any> {
    return this.httpClient.post<Contact>(`${this.contactUrl}`, contact);
  }

  update(id:number, contact:Contact): Observable<any> {
    return this.httpClient.put<Contact>(`${this.contactUrl}/${id}`, contact);
  }

  delete(id:number): Observable<any> {
    return this.httpClient.delete<Contact>(`${this.contactUrl}/${id}`);
  }
}
