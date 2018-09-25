import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EmpAppApiResponse } from './emp-app-api-response';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private http: HttpClient) { }

  getEmployees() {
    return this.http.get<EmpAppApiResponse>(`http://localhost:8081/employees`);
  }
}
