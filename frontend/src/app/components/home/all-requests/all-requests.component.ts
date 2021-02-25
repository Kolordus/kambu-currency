import {Component, OnInit} from '@angular/core';
import {HttpService, PersistedRequest} from "../../../services/http.service";
import {Observable} from "rxjs";
import {share} from "rxjs/operators";

@Component({
  selector: 'app-all-requests',
  templateUrl: './all-requests.component.html',
  styleUrls: ['./all-requests.component.css']
})
export class AllRequestsComponent implements OnInit{

  allRates$: Observable<Array<PersistedRequest>>

  constructor(private http: HttpService) {
  }

  getAllRequests() {
    this.allRates$ = this.http.getAllRequests().pipe(share());
  }

  ngOnInit(): void {
    this.getAllRequests();
  }

}

