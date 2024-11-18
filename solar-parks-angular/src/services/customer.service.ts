import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Constants} from "../util/constants";
import {Observable} from "rxjs";
import {Customer} from "../models/customer";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private httpClient = inject(HttpClient);
  private customerUrl = Constants.SERVER_HOSTNAME + "/customers";

  getAll():Observable<any> {
    return this.httpClient.get<Customer[]>(this.customerUrl);
  }

  getById(id: number): Observable<any> {
    return this.httpClient.get<Customer>(`${this.customerUrl}/${id}`);
  }

  create(customer:Customer): Observable<any> {
    return this.httpClient.post<Customer>(`${this.customerUrl}`, customer);
  }

  update(id?:number, customer?:Customer): Observable<any> {
    return this.httpClient.put<Customer>(`${this.customerUrl}/${id}`, customer);
  }

  delete(id:number): Observable<any> {
    return this.httpClient.delete<Customer>(`${this.customerUrl}/${id}`);
  }
}
