import { FollowerAddRequest } from './../shared/models/FollowerAddRequest';
import { FormBuilder, FormGroup } from '@angular/forms';
import { environment } from 'src/environments/environment';
import { Component, OnInit } from '@angular/core';
import { RequestFollowers } from '../shared/models/RequestFollowers';
import { ServiceService } from '../shared/service.service';
import { User } from '../shared/models/User';
import { Router } from '@angular/router';

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
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit() {
    this.listFollowers();
    this.listarUsers();
    this.fbPost = this.formBuilder.group({
     text: [null]
    });

  }

  listarUsers(){
    this.service.listUsers().subscribe(
      dados => {
        this.listaUsers = dados.filter(dado=> dado.id !== 1);
        for(const item of this.requestFollowers.content){
          this.listaUsers = this.listaUsers.filter(dado=> dado.id !== item.followerId);
        }
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
    this.service.removeFollower(1,followerId).subscribe(
      data=>{
        this.ngOnInit();
      }
    );
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
    const follower = new FollowerAddRequest();
    follower.followerId = followerId;
    this.service.addFollower(1,follower).subscribe(
      data=>{
        this.ngOnInit();
        this.listarUsers();
      }
    );
  }
}

