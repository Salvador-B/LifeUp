// src/app/services/achievement-toast.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface AchievementToast {
  id: number;
  title: string;
  subtitle?: string;
  icon?: string;
  duration?: number;
}

@Injectable({ providedIn: 'root' })
export class AchievementToastService {
  private counter = 0;
  private _toasts$ = new BehaviorSubject<AchievementToast[]>([]);
  toasts$ = this._toasts$.asObservable();

  show(title: string, subtitle?: string, icon = '🏆', duration = 4000) {
    const id = ++this.counter;
    const toast: AchievementToast = { id, title, subtitle, icon, duration };
    this._toasts$.next([...this._toasts$.value, toast]);
    setTimeout(() => this.dismiss(id), duration);
  }

  dismiss(id: number) {
    this._toasts$.next(this._toasts$.value.filter(t => t.id !== id));
  }

  clear() { this._toasts$.next([]); }
}
