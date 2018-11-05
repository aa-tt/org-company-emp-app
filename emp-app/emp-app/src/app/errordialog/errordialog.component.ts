import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Info } from '../info';
import { InfoMessagingService } from '../info-messaging.service';

@Component({
  selector: 'app-errordialog',
  templateUrl: './errordialog.component.html',
  styleUrls: ['./errordialog.component.css']
})
export class ErrordialogComponent implements OnDestroy {

  errorSubscription: Subscription;
  info: Info = undefined;

  constructor(private errorService: InfoMessagingService) {
    this.errorSubscription = this.errorService.getMessage().subscribe((obj: Info) => {
      this.info = obj;
    });
  }

  ngOnDestroy() {
    this.errorSubscription.unsubscribe();
  }

  onError(): boolean {
    if (this.info && this.info.status != '200') {
      return true;
    }
    return false;
  }

  onSuccess(): boolean {
    if (this.info && this.info.status == '200') {
      return true;
    }
    return false;
  }

  close() {
    this.errorService.clearMessage();
  }

}
