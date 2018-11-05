import { Component, OnInit } from '@angular/core';
import { AppService } from './app.service';
import { Employee } from './employee';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'earth';

  constructor() {}

  ngOnInit() { }
}
