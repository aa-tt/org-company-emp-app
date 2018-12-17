import { NgModule } from '@angular/core';
import { RouterModule, Routes, Router } from '@angular/router';
import { LoadempComponent } from './loademp/loademp.component';
import { AddempComponent } from './addemp/addemp.component';

const routes: Routes = [
  { path: 'load', component: LoadempComponent },
  { path: 'add', component: AddempComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }