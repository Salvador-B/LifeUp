import { CommonModule } from '@angular/common';
import { Component, EventEmitter, HostListener, Input, Output } from '@angular/core';
import { DailyTask, DailyTaskService } from '../../services/daily-task.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-daily-task',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './daily-task.component.html'
})
export class DailyTaskComponent {
  @Input() dailyTask!: DailyTask;
  @Output() edit = new EventEmitter<DailyTask>();
  @Output() refresh = new EventEmitter<void>();
  @Output() xpGained = new EventEmitter<number>();

  menuOpen = false;

  constructor(private dailyTaskService: DailyTaskService, private auth: AuthService) {}

  @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.menu-container')) {
      this.menuOpen = false;
    }
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  editDailyTask() {
    this.edit.emit(this.dailyTask);
    this.menuOpen = false;
  }

  deleteDailyTask() {
    if (confirm(`¿Eliminar misión diaria "${this.dailyTask.title}"?`)) {
      this.dailyTaskService.deleteDailyTask(this.dailyTask.id).subscribe(() => {
        this.refresh.emit();
      });
    }
  }

  completeDailyTask() {
    this.dailyTaskService.completeDailyTask(this.dailyTask.id).subscribe(updated => {
      this.dailyTask = updated;
      this.refresh.emit();
      this.xpGained.emit(updated.points);
      this.auth.me().subscribe();
    });
  }
}
