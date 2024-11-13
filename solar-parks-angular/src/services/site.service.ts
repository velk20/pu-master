import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Constants} from "../util/constants";
import {Observable} from "rxjs";
import {Contact} from "../models/contact";
import {Project} from "../models/project";
import {Site} from "../models/site";

@Injectable({
  providedIn: 'root'
})
export class SiteService {
  private httpClient = inject(HttpClient);
  private siteUrl = Constants.SERVER_HOSTNAME + "/sites";

  getAll(): Observable<any> {
    return this.httpClient.get(this.siteUrl);
  }

  getAllByProjectId(projectId: number): Observable<any> {
    return this.httpClient.get<Site[]>(`${this.siteUrl}/project/${projectId}`);
  }

  getById(id:number): Observable<any> {
    return this.httpClient.get<Site>(`${this.siteUrl}/${id}`);
  }

  create(site:Site): Observable<any> {
    return this.httpClient.post<Site>(`${this.siteUrl}`, site);
  }

  update(id:number, site:Site): Observable<any> {
    return this.httpClient.put<Site>(`${this.siteUrl}/${id}`, site);
  }

  delete(id:number): Observable<any> {
    return this.httpClient.delete<Site>(`${this.siteUrl}/${id}`);
  }
}
