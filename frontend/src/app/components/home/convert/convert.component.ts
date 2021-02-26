import {Component, OnInit} from '@angular/core';
import {HttpService} from '../../../services/http.service';
import {Observable} from 'rxjs';
import {share} from 'rxjs/operators';

@Component({
  selector: 'app-rated-surveys',
  templateUrl: './convert.component.html',
  styleUrls: ['./convert.component.css']
})
export class ConvertComponent implements OnInit {

  availableCurrencies$: Observable<Array<string>>;
  convertedValue$: Observable<number>;

  selectedBase: string;
  selectedDesired: string;
  amount: number;

  constructor(private http: HttpService) {
  }

  ngOnInit(): void {
    this.availableCurrencies$ = this.http.getAllAvailableCurrencies().pipe(share());
  }

  convert(amount = 1, base = 'pln', desired = 'eur') {
    this.convertedValue$ = this.http.convert(amount, base, desired).pipe(share());
  }


}
