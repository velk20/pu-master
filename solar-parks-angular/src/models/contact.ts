import {Project} from "./project";

export type Contact = {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  projects: Project[]
}

export type ProjectContact = {
  id: number;
}
