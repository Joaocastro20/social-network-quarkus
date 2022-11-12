import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from './models/User';

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
}
