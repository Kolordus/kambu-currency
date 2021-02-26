import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class HttpService {

  connectionUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {
  }

  getAllAvailableCurrencies(): Observable<Array<string>> {
    return this.http.get<Array<string>>(this.connectionUrl + '/all-available-currencies');
  }

  getAllRequests(): Observable<Array<PersistedRequest>> {
    return this.http.get<Array<PersistedRequest>>(this.connectionUrl + '/all-requests');
  }

  convert(amount?: number, base?: string, desired?: string): Observable<number> {
    return this.http.get<number>(this.connectionUrl + '/convert?amount=' + amount + '&base=' + base + '&desired=' + desired);
  }

  getRates(base = 'PLN', currencies: Array<string> = []): Observable<Map<string, number>> {
    let url = this.connectionUrl + '/rates?base=' + base;
    if (currencies === []) {
      url += '&currencies=' + currencies;
    }
    return this.http.get<Map<string, number>>(url);
  }

}

export interface PersistedRequest {
  requestUrl: string;
  timeCreated: Date;
  amount?: number;
  baseCurrency?: string;
  desiredCurrencies?: Map<string, number>;
  invokedExternalApiUrls?: Array<string>;
}




