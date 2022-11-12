import { UserRequest } from './../shared/models/UserRequest';
import { User } from './../shared/models/User';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ServiceService } from '../shared/service.service';
import { OverlayEventDetail } from '@ionic/core';
import { IonModal } from '@ionic/angular';

@Component({
  selector: 'app-user-crud',
  templateUrl: './user-crud.page.html',
  styleUrls: ['./user-crud.page.scss'],
})
export class UserCrudPage implements OnInit {

  @ViewChild(IonModal) modal: IonModal;

  message = 'This modal example uses triggers to automatically open a modal when the button is clicked.';
  name: string;
  age: number;

  user: User;

  listUsers: User[];

  constructor(private service: ServiceService) {}

  ngOnInit() {
    this.service.listUsers().subscribe((list: User[]) => {
      this.listUsers = list;
    });
  }

  cancel() {
    this.modal.dismiss(null, 'cancel');
  }

  confirm() {
    this.service.addUser(this.user).subscribe();
    this.modal.dismiss(null, 'confirm');

  }

  onWillDismiss($event: Event){
    const ev = event as CustomEvent<OverlayEventDetail<string>>;
    if (ev.detail.role === 'confirm') {
      this.message = `Hello, ${ev.detail.data}!`;
    }
  }
}
