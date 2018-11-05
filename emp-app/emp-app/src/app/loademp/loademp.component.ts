import { Component, OnInit } from '@angular/core';
import { Employee } from '../employee';
import { AppService } from '../app.service';

@Component({
  selector: 'app-loademp',
  templateUrl: './loademp.component.html',
  styleUrls: ['./loademp.component.css']
})
export class LoadempComponent implements OnInit {

  employees: Employee[] = [];

  constructor(private service: AppService) {}

  ngOnInit() {
    this.service.getEmployees().subscribe((e) => {
      this.employees = <Employee[]> e.data;
    });
  }

}
