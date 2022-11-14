import { RequestPost } from './models/RequestPost';
import { UserRequest } from './models/UserRequest';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from './models/User';
import { RequestFollowers } from './models/RequestFollowers';
import { Follower } from './models/Follower';
import { Post } from './models/Post';
import { FollowerAddRequest } from './models/FollowerAddRequest';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {

  // eslint-disable-next-line @typescript-eslint/naming-convention
  private readonly API = `${environment.API}`;

  constructor(
    private http: HttpClient
  ) { }

  listUsers(): Observable<User[]>{
    return this.http.get<User[]>(`${this.API}users`);
  }

  addUser(user: User): Observable<User>{
    return this.http.post<any>(`${this.API}users`,user);
  }

  listFollowers(id: number): Observable<RequestFollowers>{
    return this.http.get<RequestFollowers>(`${this.API}users/${id}/followers`);
  }

  removeFollower(id: number,followerId: number): Observable<any>{
    return this.http.delete<Follower>(`${this.API}users/${id}/followers?followerId=${followerId}`);
  }

  addPost(id: number,requestPost: RequestPost): Observable<Post>{
    return this.http.post<Post>(`${this.API}users/${id}/posts`, requestPost);
  }

  addFollower(id: number, followerId: FollowerAddRequest){
    return this.http.put(`${this.API}users/${id}/followers`, followerId);
  }
}
