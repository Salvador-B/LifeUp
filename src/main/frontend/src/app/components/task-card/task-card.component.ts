import { Component, Input, Output, EventEmitter, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Task, TaskService } from '../../services/task.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-task-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './task-card.component.html'
})
export class TaskCardComponent {
  @Input() task!: Task;
  @Output() edit = new EventEmitter<Task>();
  @Output() refresh = new EventEmitter<void>();
  @Output() xpGained = new EventEmitter<number>();

  menuOpen = false;

  constructor(private taskService: TaskService, private auth: AuthService) {}

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

  editTask() {
    this.edit.emit(this.task);
    this.menuOpen = false;
  }

  deleteTask() {
    if (confirm(`¿Eliminar la misión "${this.task.title}"?`)) {
      this.taskService.deleteTask(this.task.id).subscribe(() => {
        this.refresh.emit();
      });
    }
  }

  completeTask() {
    this.taskService.completeTask(this.task.id).subscribe(updated => {
      this.task = updated;
      this.refresh.emit();
      this.xpGained.emit(updated.points);
      this.auth.me().subscribe();
    });
  }
}
