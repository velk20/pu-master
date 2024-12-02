import {Customer} from "./customer";
import {Contact, ProjectContact} from "./contact";

export type Project = {
  id: number;
  name: string;
  cost: number;
  customer: Customer;
  contacts: Contact[];
  active: boolean;
}

export type CreateProject = {
  name: string;
  cost: number;
  customer: number;
  contacts: ProjectContact[];
  active: boolean;
}
