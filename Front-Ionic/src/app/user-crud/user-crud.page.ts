import { User } from './../shared/models/User';
import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../shared/service.service';

@Component({
  selector: 'app-user-crud',
  templateUrl: './user-crud.page.html',
  styleUrls: ['./user-crud.page.scss'],
})
export class UserCrudPage implements OnInit {
  listUsers: User[];

  constructor(private service: ServiceService) {}

  ngOnInit() {
    this.service.listUsers().subscribe((list: User[]) => {
      this.listUsers = list;
    });
  }
}
