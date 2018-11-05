import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Employee } from '../employee';
import { AppService } from '../app.service';
import { InfoMessagingService } from '../info-messaging.service';
import { Info } from '../info';

@Component({
  selector: 'app-addemp',
  templateUrl: './addemp.component.html',
  styleUrls: ['./addemp.component.css']
})
export class AddempComponent implements OnInit {

  addEmpFg: FormGroup;

  constructor(private appService: AppService, private messagingService: InfoMessagingService) { }

  ngOnInit() {
    this.addEmpFg = new FormGroup({
      name: new FormControl('', Validators.required),
      dept: new FormControl('', Validators.required),
      age: new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.min(0), Validators.max(100)])
    });
  }

  onSubmit({ value, valid }: { value: Employee, valid: boolean }) {
    let employees: Employee[] = [];
    employees.push(value);
    this.appService.postEmployees(employees).subscribe(e => {
      const info = new Info();
      info.status = '200';
      info.message = 'Saved employees ';
      const emps = <Employee[]>e.data;
      for (let i = 0; i < emps.length; i++) {
        info.message = info.message.concat(emps[i].name + " with id=" + emps[i].id + " ");
      }
      this.messagingService.sendMessage(info);
    });
  }
}
