import { Component, OnInit, OnChanges } from '@angular/core';
import { Employee } from '../employee';
import { AppService } from '../app.service';
import { Subscription } from 'rxjs';
import { InfoMessagingService } from '../info-messaging.service';
import { FormGroup, FormControl } from '@angular/forms';
import { Info } from '../info';

@Component({
  selector: 'app-loademp',
  templateUrl: './loademp.component.html',
  styleUrls: ['./loademp.component.css']
})
export class LoadempComponent implements OnInit, OnChanges {

  empAddObserver: Subscription;
  fg: FormGroup;
  employees: Employee[] = [];

  constructor(private service: AppService, private empInfoService: InfoMessagingService) {
    this.empAddObserver = this.empInfoService.getEmployeeMessage().subscribe((obj: Employee[]) => {
      obj.forEach((e: Employee) => {
        this.employees.push(e);
      });
      const info = new Info();
      info.status = '200';
      info.message = 'Loaded employees ' + this.employees.length;
      this.empInfoService.sendMessage(info);
    });
  }

  ngOnChanges() {
    this.fg.valueChanges.subscribe((value) => console.log('changed value--', value));
  }

  ngOnInit() {
    this.service.getEmployees().subscribe((e) => {
      this.employees = <Employee[]>e.data;
    });
    this.fg = new FormGroup({
      employees: new FormControl('')
    });
  }

}
