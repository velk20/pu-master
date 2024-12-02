import {Component, EventEmitter, Input, Output} from "@angular/core";

export type DataGridHeader = {
  column: string;
  value: string;
}
@Component({
  selector: 'cc-data-grid',
  templateUrl: './data-grid.component.html',
  standalone: true,
  styleUrls: ['./data-grid.component.css']
})
export class DataGridComponent {
  @Input()
  public inputHeaderConfig: DataGridHeader[] = [];
  @Input()
  public inputDataSource: any;
  @Input()
  public isEditable = false;
  @Input()
  public isRemovable = false;
  @Input()
  public isNavigatable = false;


  @Output()
  public onEdit = new EventEmitter();
  @Output()
  public onRemove = new EventEmitter();
  @Output()
  public onNavigate = new EventEmitter();
}
