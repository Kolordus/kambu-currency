import {Component} from '@angular/core';
import {HttpService, PersistedRequest} from "../../../services/http.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-all-requests',
  templateUrl: './all-requests.component.html',
  styleUrls: ['./all-requests.component.css']
})
export class AllRequestsComponent  {

  allRates$: Observable<Array<PersistedRequest>>

  constructor(private http: HttpService) {
    this.getAllRequests();
  }


  getAllRequests() {
    this.allRates$ = this.http.getAllRequests();
  }

}

