import { UserRequest } from './../shared/models/UserRequest';
import { User } from './../shared/models/User';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ServiceService } from '../shared/service.service';
import { OverlayEventDetail } from '@ionic/core';
import { IonModal } from '@ionic/angular';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Route, Router } from '@angular/router';

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

  fb: FormGroup;

  constructor(
    private service: ServiceService,
    private formBuilder: FormBuilder,
    private router: Router
    ) {}

  ngOnInit() {
    this.fb = this.formBuilder.group({
      name: [null],
      age: [null]
    });

    this.service.listUsers().subscribe((list: User[]) => {
      this.listUsers = list;
    });
  }

  updateForm(){
    this.fb.patchValue({
      name: this.name,
      age: this.age
    });
  }

  cancel() {
    this.modal.dismiss(null, 'cancel');
  }

  confirm() {
    this.updateForm();
    this.service.addUser(this.fb.value).subscribe();
    this.modal.dismiss(null, 'confirm');
    this.router.navigate(['']);
  }

  onWillDismiss($event: Event){
    const ev = event as CustomEvent<OverlayEventDetail<string>>;
    if (ev.detail.role === 'confirm') {
      this.message = `Hello, ${ev.detail.data}!`;
    }
  }
}
