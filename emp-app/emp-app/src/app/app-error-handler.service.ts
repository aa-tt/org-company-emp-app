import { ErrorHandler, Injectable, Injector } from "@angular/core";
import { HttpErrorResponse } from "@angular/common/http";
import { EmpAppApiResponse } from "./emp-app-api-response";
import { Info } from "./info";
import { InfoMessagingService } from "./info-messaging.service";

@Injectable({ providedIn: 'root' })
export class AppErrorHandler implements ErrorHandler {

    constructor(private injector: Injector) { }

    handleError(error) {
        const messagingService = this.injector.get(InfoMessagingService);
        if (error instanceof HttpErrorResponse) {
            const resp: EmpAppApiResponse = (<HttpErrorResponse>error).error;
            const obj = new Info();
            obj.status = error.status.toString();
            obj.message = resp.error['displayMessage'];
            messagingService.sendMessage(obj);
        }
    }
}