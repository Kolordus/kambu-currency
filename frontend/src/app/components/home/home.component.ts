import {Component} from '@angular/core';
import {HttpService} from '../../services/http.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent  {

  constructor(private http: HttpService,
              private router: Router,
              private activatedRoute: ActivatedRoute) { }


  showRated() {
    this.router.navigate(['convert'], {relativeTo: this.activatedRoute});
  }

  showUnrated() {
    this.router.navigate(['get-rates'], {relativeTo: this.activatedRoute});
  }

  showAllRequests() {
    this.router.navigate(['all-requests'], {relativeTo: this.activatedRoute});
  }
}
