import {Customer} from "./customer";
import {Contact} from "./contact";

export type Project = {
  id: number;
  name: string;
  cost: number;
  customer: Customer;
  contacts: Contact[];
}
