import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskService, Task } from './services/task.service';
import { UserStatsComponent } from './components/user-stats/user-stats.component';
import { TaskCardComponent } from './components/task-card/task-card.component';
import { FormsModule } from '@angular/forms';
import { DailyTaskComponent } from './components/daily-task/daily-task.component';
import { DailyTask, DailyTaskService, DayOfWeek } from './services/daily-task.service';
import { AuthService } from './services/auth.service';
import { AuthComponent } from './components/auth/auth.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, UserStatsComponent, TaskCardComponent, DailyTaskComponent, AuthComponent],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  @ViewChild(UserStatsComponent) userStatsComponent!: UserStatsComponent;

  tasks: Task[] = [];
  dailyTasks: DailyTask[] = [];
  showModal = false;
  dailyShowModal = false;
  isEditing = false;
  dailyIsEditing = false;
  taskForm: Partial<Task> = { title: '', description: '', difficulty: 'NORMAL'};
  dailyTaskForm: Partial<DailyTask> = {
    title: '',
    description: '',
    difficulty: 'NORMAL',
    startDate: this.getTodayDate(),
    recurrenceType: 'DIARIO',
    repeatEvery: 1,
    daysOfWeek: [] as DayOfWeek[]
  };
  activeTab: 'tasks' | 'daily' = 'tasks';
  allDays: DayOfWeek[] = ['MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY'];
  showCompleted = true;

  constructor(
    private taskService: TaskService,
    private dailyTaskService: DailyTaskService,
    public auth: AuthService
  ) {}

  ngOnInit(): void {
    const token = this.auth.getToken();
    if (token) {
      this.auth.me().subscribe(); // se rehidrata aquí, ya sin ciclo
    }

    this.auth.user$.subscribe(user => {
      if (user) {
        this.loadData();
      }
    });
  }

  loadData() {
    this.taskService.getTasks().subscribe(data => this.tasks = data);
    this.dailyTaskService.getDailyTasks().subscribe(data => this.dailyTasks = data);
  }

  openCreateModal() {
    this.isEditing = false;
    this.taskForm = { title: '', description: '', difficulty: 'NORMAL'};
    this.showModal = true;
  }

  openEditModal(task: Task) {
    this.isEditing = true;
    this.taskForm = { ...task };
    this.showModal = true;
  }

  saveTask() {
    if (this.isEditing && this.taskForm.id) {
      this.taskService.updateTask(this.taskForm.id, this.taskForm).subscribe(() => {
        this.loadData();
        this.showModal = false;
      });
    } else {
      this.taskService.createTask(this.taskForm).subscribe(() => {
        this.loadData();
        this.showModal = false;
      });
    }
  }

  openCreateDailyModal() {
    this.dailyIsEditing = false;
    this.dailyTaskForm = {
      title: '',
      description: '',
      difficulty: 'NORMAL',
      startDate: this.getTodayDate(),
      recurrenceType: 'DIARIO',
      repeatEvery: 1,
      daysOfWeek: []
    };
    this.dailyShowModal = true;
  }

  openEditDailyModal(task: DailyTask) {
    this.dailyIsEditing = true;
    this.dailyTaskForm = { ...task };
    this.dailyShowModal = true;
  }

  toggleDay(day: DayOfWeek) {
    if (!this.dailyTaskForm.daysOfWeek) this.dailyTaskForm.daysOfWeek = [];
    if (this.dailyTaskForm.daysOfWeek.includes(day)) {
      this.dailyTaskForm.daysOfWeek = this.dailyTaskForm.daysOfWeek.filter(d => d !== day);
    } else {
      this.dailyTaskForm.daysOfWeek.push(day);
    }
  }

  saveDailyTask() {
    if (this.dailyIsEditing && this.dailyTaskForm.id) {
      this.dailyTaskService.updateDailyTask(this.dailyTaskForm.id, this.dailyTaskForm).subscribe(() => {
        this.loadData();
        this.dailyShowModal = false;
      });
    } else {
      this.dailyTaskService.createDailyTask(this.dailyTaskForm).subscribe(() => {
        this.loadData();
        this.dailyShowModal = false;
      });
    }
  }

  getTodayDate(): string {
    const today = new Date();
    return today.toISOString().split('T')[0]; // "2025-08-20"
  }

  get pendingDailyTasks(): DailyTask[] {
    return this.dailyTasks.filter(t => !t.doneToday);
  }

  get completedDailyTasks(): DailyTask[] {
    return this.dailyTasks.filter(t => t.doneToday);
  }

  logout() {
    this.auth.logout();
    this.tasks = [];
    this.dailyTasks = [];
  }
}
