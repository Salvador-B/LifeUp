import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Task {
  id: string;
  title: string;
  description: string;
  dueDate: string;
  done: boolean;
  points: number;
  createdAt: string;
  difficulty: 'FACIL' | 'NORMAL' | 'DIFICIL' | 'MUY_DIFICIL';
}

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private apiUrl = '/api/tasks'; // URL del backend

  constructor(private http: HttpClient) {}

  getTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.apiUrl);
  }

  createTask(task: Partial<Task>) {
    return this.http.post<Task>(this.apiUrl, task);
  }

  updateTask(id: string, task: Partial<Task>) {
    return this.http.put<Task>(this.apiUrl + `/${id}`, task);
  }

  deleteTask(id: string) {
    return this.http.delete(this.apiUrl + `/${id}`);
  }

  completeTask(id: string) {
    return this.http.patch<Task>(`${this.apiUrl}/${id}/complete`, {});
  }
}
