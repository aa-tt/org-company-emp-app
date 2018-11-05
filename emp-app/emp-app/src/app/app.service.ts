import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { EmpAppApiResponse } from './emp-app-api-response';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AppService {

  constructor(private http: HttpClient) { }

  getEmployees(): Observable<any> {
    return this.http
      .get<EmpAppApiResponse>(`http://localhost:8081/empapp-api/v1/employees`);
  }
  postEmployees(obj): Observable<any> {
    return this.http
      .post<EmpAppApiResponse>(`http://localhost:8081/empapp-api/v1/employees`, obj);
  }
}
