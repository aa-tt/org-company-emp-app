import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { Info } from "./info";
import { Employee } from "./employee";

@Injectable({ providedIn: 'root' })
export class InfoMessagingService {
    private errorSubject = new Subject<any>();
    private empAddSubject = new Subject<any>();

    /** error and success information messaging */
    sendMessage(obj: Info) {
        this.errorSubject.next(obj);
    }
    clearMessage() {
        this.errorSubject.next();
    }
    getMessage(): Observable<Info> {
        return this.errorSubject.asObservable();
    }
    
    /** employee     information messaging */
    sendEmployeeMessage(employees: Employee[]) {
        this.empAddSubject.next(employees);
    }
    clearEmployeeMessage() {
        this.empAddSubject.next();
    }
    getEmployeeMessage(): Observable<Employee[]> {
        return this.empAddSubject.asObservable();
    }
}