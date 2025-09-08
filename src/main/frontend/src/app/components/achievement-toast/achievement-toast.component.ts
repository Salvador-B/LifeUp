import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AchievementToast, AchievementToastService } from '../../services/achievement-toast.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-achievement-toast',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './achievement-toast.component.html',
  styleUrl: './achievement-toast.component.css'
})
export class AchievementToastComponent {
  toasts$!: Observable<AchievementToast[]>;

  constructor(private toastService: AchievementToastService) {
    this.toasts$ = this.toastService.toasts$;
  }

  trackById(_i: number, t: AchievementToast) { return t.id; }
  close(id: number) { this.toastService.dismiss(id); }
}
