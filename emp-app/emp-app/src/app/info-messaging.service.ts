import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { Info } from "./info";

@Injectable({ providedIn: 'root' })
export class InfoMessagingService {
    private errorSubject = new Subject<any>();

    sendMessage(obj: Info) {
        this.errorSubject.next(obj);
    }
    clearMessage() {
        this.errorSubject.next();
    }
    getMessage(): Observable<Info> {
        return this.errorSubject.asObservable();
    }
}