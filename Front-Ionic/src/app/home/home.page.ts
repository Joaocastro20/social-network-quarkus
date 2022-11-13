import { environment } from 'src/environments/environment';
import { Component, OnInit } from '@angular/core';
import { RequestFollowers } from '../shared/models/RequestFollowers';
import { ServiceService } from '../shared/service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  requestFollowers: RequestFollowers;

  constructor(
    private service: ServiceService
  ) { }

  ngOnInit() {
    this.listFollowers();

  }

  listFollowers(){
    this.service.listFollowers(1).subscribe(
      dados => {
        this.requestFollowers = dados;
      }
    );
  }

  deletFollower(id: number){
    // this.service.removeFollower(1,13).subscribe();
    window.alert('em breve ...');
    this.listFollowers();
  }

}
