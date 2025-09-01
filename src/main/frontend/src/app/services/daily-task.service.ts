import { Injectable } from '@angular/core';
import { User } from './user.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export type DayOfWeek =
  | 'MONDAY'
  | 'TUESDAY'
  | 'WEDNESDAY'
  | 'THURSDAY'
  | 'FRIDAY'
  | 'SATURDAY'
  | 'SUNDAY';

export interface DailyTask {
  id: string;
  user: User;
  title: string;
  description: string;
  doneToday: boolean;
  lastCompletedDate: string;
  points: number;
  difficulty: 'FACIL' | 'NORMAL' | 'DIFICIL' | 'MUY_DIFICIL';
  startDate: string;
  recurrenceType: 'DIARIO' | 'SEMANAL' | 'MENSUAL' | 'ANUAL';
  repeatEvery: number;
  daysOfWeek: DayOfWeek[];
}

@Injectable({
  providedIn: 'root'
})
export class DailyTaskService {

  private apiUrl = '/api/daily-tasks';

  constructor(private http: HttpClient) { }

  getDailyTasks(): Observable<DailyTask[]> {
    return this.http.get<DailyTask[]>(this.apiUrl);
  }

  createDailyTask(dailyTask: Partial<DailyTask>) {
    return this.http.post<DailyTask>(this.apiUrl, dailyTask);
  }

  updateDailyTask(id: string, dailyTask: Partial<DailyTask>) {
    return this.http.put<DailyTask>(this.apiUrl + `/${id}`, dailyTask);
  }

  deleteDailyTask(id: string) {
    return this.http.delete(this.apiUrl + `/${id}`);
  }

  completeDailyTask(id: string) {
    return this.http.patch<DailyTask>(`${this.apiUrl}/${id}/complete`, {});
  }
}
