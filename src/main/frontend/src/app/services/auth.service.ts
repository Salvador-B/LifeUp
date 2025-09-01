import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, tap } from 'rxjs';

export interface AuthUser { 
  id:string; name:string; 
  email:string; 
  level:number; 
  experience:number; 
  coins:number;
  health: number;
  maxHealth: number;
  pass: string;
  streakCount: number;
  createdAt: string;
}
export interface AuthResponse { token:string; user:AuthUser; }

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private api = '/api/auth';
  private _user$ = new BehaviorSubject<AuthUser | null>(null);
  user$ = this._user$.asObservable();

  constructor(private http: HttpClient) {}

  getToken(){ return localStorage.getItem('token'); }
  isLoggedIn(){ return !!this.getToken(); }

  login(email:string, password:string){
    return this.http.post<AuthResponse>(`${this.api}/login`, {email, password}).pipe(
      tap(res => { localStorage.setItem('token', res.token); this._user$.next(res.user); })
    );
  }
  register(name:string, email:string, password:string){
    return this.http.post<AuthResponse>(`${this.api}/register`, {name, email, password}).pipe(
      tap(res => { localStorage.setItem('token', res.token); this._user$.next(res.user); })
    );
  }
  me(){
    return this.http.get<AuthUser>(`${this.api}/me`).pipe(tap(u => this._user$.next(u)));
  }
  logout(){
    localStorage.removeItem('token'); this._user$.next(null);
  }
}
