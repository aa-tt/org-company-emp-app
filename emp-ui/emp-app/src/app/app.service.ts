import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Employee } from './employee';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http: HttpClient) { }

  getEmployees() {
    return this.http.get<Employee[]>(`http://localhost:8081/employees`);
  }
}
