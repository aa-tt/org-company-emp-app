import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AppService } from './app.service';
import { HttpClientModule } from '@angular/common/http';
import { AddempComponent } from './addemp/addemp.component';
import { ErrordialogComponent } from './errordialog/errordialog.component';
import { AppErrorHandler } from './app-error-handler.service';
import { InfoMessagingService } from './info-messaging.service';
import { LoadempComponent } from './loademp/loademp.component';

@NgModule({
  declarations: [
    AppComponent,
    AddempComponent,
    ErrordialogComponent,
    LoadempComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    AppService,
    InfoMessagingService,
    { provide: ErrorHandler, useClass: AppErrorHandler }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
