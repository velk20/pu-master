import {Component, inject} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {HttpClient} from "@angular/common/http";
import {Customer} from "../models/customer";
import {CustomerService} from "../services/customer.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  private title = 'Solar Park';
  private customerService = inject(CustomerService);
  public customers:Customer[] = [];

  ngOnInit() {
  this.customerService.getAll().subscribe((c)=>{
    this.customers = c.data;
  })
  }

  public getTitle() {
    return this.title;
  }
}
