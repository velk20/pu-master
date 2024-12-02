import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Constants} from "../util/constants";
import {Observable} from "rxjs";
import {CreateProject, Project} from "../models/project";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private httpClient = inject(HttpClient);
  private projectUrl = Constants.SERVER_HOSTNAME + "/projects";

  getAll():Observable<any> {
    return this.httpClient.get<Project[]>(this.projectUrl);
  }

  getAllByCustomerId(customerId:number): Observable<any> {
    return this.httpClient.get<Project[]>(`${this.projectUrl}/customer/${customerId}`);
  }

  getById(id: number): Observable<any> {
    return this.httpClient.get<Project>(`${this.projectUrl}/${id}`);
  }

  create(project:CreateProject): Observable<any> {
    return this.httpClient.post<Project>(`${this.projectUrl}`, project);
  }

  update(id:number, project:Project): Observable<any> {
    return this.httpClient.put<Project>(`${this.projectUrl}/${id}`, project);
  }

  delete(id:number): Observable<any> {
    return this.httpClient.delete<Project>(`${this.projectUrl}/${id}`);
  }
}
