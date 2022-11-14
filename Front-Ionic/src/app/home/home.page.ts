import { FollowerAddRequest } from './../shared/models/FollowerAddRequest';
import { FormBuilder, FormGroup } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { Component, OnInit } from '@angular/core';
import { RequestFollowers } from '../shared/models/RequestFollowers';
import { ServiceService } from '../shared/service.service';
import { User } from '../shared/models/User';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  texto: string;

  listaUsers: User[];

  fbPost: FormGroup;

  requestFollowers: RequestFollowers;

  constructor(
    private service: ServiceService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.listFollowers();
    this.fbPost = this.formBuilder.group({
      text: [null]
    });
    this.service.listUsers().subscribe(
      dados => {
        this.listaUsers = dados;
      }
    );
  }

  listFollowers(){
    this.service.listFollowers(1).subscribe(
      dados => {
        this.requestFollowers = dados;
      }
    );
  }

  deletFollower(id: number,followerId: number){
    this.service.removeFollower(1,followerId).subscribe();
    this.listFollowers();
  }

  updateFbPost(){
    this.fbPost.patchValue({
      text: this.texto
    });
  }


  salvePost(){
    this.updateFbPost();
    this.service.addPost(1,this.fbPost.value).subscribe();
  }

  onSeguir(followerId: number){
    window.alert(followerId);
  }
}

