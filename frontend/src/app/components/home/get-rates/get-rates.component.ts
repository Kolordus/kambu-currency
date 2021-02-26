import {Component, OnInit} from '@angular/core';
import {HttpService} from '../../../services/http.service';
import {Observable} from 'rxjs';
import {share} from 'rxjs/operators';

@Component({
  selector: 'app-unrated-surveys',
  templateUrl: './get-rates.component.html',
  styleUrls: ['./get-rates.component.css']
})
export class GetRatesComponent implements OnInit {

  baseCurrency: string;
  desiredCurrencies: Array<string>;
  rates$: Observable<Map<string, number>>;
  availableCurrencies$: Observable<Array<string>>;

  constructor(private http: HttpService) {
  }

  ngOnInit(): void {
    this.availableCurrencies$ = this.http.getAllAvailableCurrencies().pipe(share());
  }

  getRates() {
    this.rates$ = this.http.getRates(this.baseCurrency, this.desiredCurrencies).pipe(share());
  }

}
