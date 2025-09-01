import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface User {
  id: string;
  name: string;
  level: number;
  experience: number;
  health: number;
  maxHealth: number;
  email: string;
  pass: string;
  coins: number;
  streakCount: number;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = '/api/user';

  constructor(private http: HttpClient) {}

  getUser(): Observable<User> {
    return this.http.get<User>(this.apiUrl);
  }
}
