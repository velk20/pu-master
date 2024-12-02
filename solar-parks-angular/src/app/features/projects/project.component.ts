import {Component, inject, Input, OnInit} from "@angular/core";
import {ProjectService} from "../../../services/project.service";
import {ActivatedRoute, ActivatedRouteSnapshot} from "@angular/router";
import {CreateProject, Project} from "../../../models/project";
import {DataGridComponent, DataGridHeader} from "../../components/data-grid/data-grid.component";
import {FormsModule} from "@angular/forms";
import {CustomerService} from "../../../services/customer.service";

@Component({
  selector: 'page-project',
  templateUrl: 'project.component.html',
  standalone: true,
  imports: [
    DataGridComponent,
    FormsModule
  ],
  styleUrls: ['project.component.css']
})
export  class ProjectPage implements OnInit {
  state = {
    isCreateNewProjectVisible : false
  }
  project: CreateProject={
    name: '',
    cost: 0,
    customer: -1,
    contacts:[],
    active: true
  };

  private activeRouter = inject(ActivatedRoute);
  projectService: ProjectService = inject(ProjectService);
  customerService: CustomerService = inject(CustomerService);
  headerConfig: DataGridHeader[] = [
    {column: 'Project name', value: 'name'},
    {column: 'Project cost', value: 'cost'},
  ]
  projects: Project[] = [];

  @Input()

  ngOnInit(): void {
    this.fetchProjects();
  }

  fetchProjects() {
    let customerId = this.activeRouter.snapshot.paramMap.get('id');
    if (customerId) {
      this.customerService.getById(+customerId).subscribe(res=>{
        this.project.customer = res.data;
      });
    }

    if (customerId) {
      this.projectService.getAllByCustomerId(+customerId).subscribe(res=>{
        this.projects = res.data;
      })
    }
  }


  processOnSave() {
this.projectService.create(this.project).subscribe(()=>{
  this.fetchProjects();
})
  }
}
