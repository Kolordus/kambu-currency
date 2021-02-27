import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {ConvertComponent} from './components/home/convert/convert.component';
import {GetRatesComponent} from './components/home/get-rates/get-rates.component';
import {AllRequestsComponent} from './components/home/all-requests/all-requests.component';


export const routes: Routes = [
    {path: '', redirectTo: 'home', pathMatch: 'full'},
    {
      path: 'home',
      component: HomeComponent,
      children: [
        {path: 'convert', component: ConvertComponent},
        {path: 'get-rates', component: GetRatesComponent},
        {path: 'all-requests', component: AllRequestsComponent}
      ]
    }
    ,
    {path: '**', component: NotFoundComponent}
  ]
;

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

export const routingComponents = [
  HomeComponent,
  ConvertComponent,
  GetRatesComponent,
  NotFoundComponent
];
