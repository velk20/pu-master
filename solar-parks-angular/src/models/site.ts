import {Project} from "./project";

export type Site = {
  id: number;
  name: string;
  address: string;
  configCost: number;
  otherCost: number;
  project: Project;
}
