import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Achievement } from '../components/achievement-list/achievement-list.component';

@Injectable({
  providedIn: 'root'
})
export class AchievementService {

  private apiUrl = '/api/achievements';

  constructor(private http: HttpClient) {}

  getUserAchievements(userId: string): Observable<Achievement[]> {
    return this.http.get<Achievement[]>(`${this.apiUrl}/user/${userId}`);
  }
}
