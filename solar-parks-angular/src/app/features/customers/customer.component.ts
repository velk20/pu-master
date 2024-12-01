import {Component, inject} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {CustomerService} from "../../../services/customer.service";
import {Customer} from "../../../models/customer";

@Component({
  selector: 'page-customer',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.css'
})
export class CustomerPage {
  private title = 'Solar Park';
  private customerService = inject(CustomerService);
  public customers: Customer[] = [];
  public isCreateFormVisible: boolean = false;
  public isEditFormVisible: boolean = false;
  public selectedCustomer: Customer | null = null;

  ngOnInit() {
    this.fetchAllCustomers();
  }

  private fetchAllCustomers() {
    this.customerService.getAll().subscribe((c) => {
      this.customers = c.data;
      console.log(c.data)
    })
  }

  public getTitle() {
    return this.title;
  }

  public processOnEdit($customer: Customer) {
    this.isEditFormVisible = true;
    this.selectedCustomer = $customer;
  }



  public processOnSave() {
    this.customerService.update(this.selectedCustomer?.id, this.selectedCustomer!).subscribe((res) => {
      console.log(res)
      this.fetchAllCustomers();
    });
  }

  public processOnChangeCustomerName($customerInput: string) {
    if (this.selectedCustomer) {
      this.selectedCustomer.name = $customerInput;
    }
  }

  public processOnCreate() {
    this.isCreateFormVisible = true;
    this.selectedCustomer = null;
  }

  public processOnCreateCustomer($inputValue: string) {
    this.customerService.create({name: $inputValue, active: 1}).subscribe((res) => {
      console.log(res)
      this.fetchAllCustomers();
    })

  }

  public processOnDelete(customer: Customer) {
    this.customerService.delete(customer.id!).subscribe((res) => {
      console.log(res);
      this.fetchAllCustomers()
    })
  }
}
