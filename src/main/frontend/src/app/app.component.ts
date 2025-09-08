import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TaskService, Task } from './services/task.service';
import { UserStatsComponent } from './components/user-stats/user-stats.component';
import { TaskCardComponent } from './components/task-card/task-card.component';
import { FormsModule } from '@angular/forms';
import { DailyTaskComponent } from './components/daily-task/daily-task.component';
import { DailyTask, DailyTaskService, DayOfWeek } from './services/daily-task.service';
import { AuthService, AuthUser } from './services/auth.service';
import { AuthComponent } from './components/auth/auth.component';
import { Achievement, AchievementListComponent } from './components/achievement-list/achievement-list.component';
import { AchievementService } from './services/achievement.service';
import { filter, switchMap } from 'rxjs';
import { AchievementToastComponent } from './components/achievement-toast/achievement-toast.component';
import { AchievementToastService } from './services/achievement-toast.service';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, UserStatsComponent, TaskCardComponent, DailyTaskComponent, AuthComponent, AchievementListComponent, AchievementToastComponent],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  @ViewChild(UserStatsComponent) userStatsComponent!: UserStatsComponent;

  tasks: Task[] = [];
  dailyTasks: DailyTask[] = [];
  achievements: Achievement[] = [];
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
  activeTab: 'tasks' | 'daily' | 'achievements' = 'tasks';
  allDays: DayOfWeek[] = ['MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY'];
  showCompleted = true;

  lastAchievementIds = new Set<string>;
  achievementsLoadedOnce = false

  constructor(
    private taskService: TaskService,
    private dailyTaskService: DailyTaskService,
    private achievementService: AchievementService,
    private toast: AchievementToastService,
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

    this.auth.user$.pipe(
      filter((user): user is AuthUser => !!user), // solo si no es null
      switchMap(user =>
        this.achievementService.getUserAchievements(user.id)
      )
    ).subscribe(list => {
      // si es la primera vez que cargamos logros, guardamos y NO mostramos toasts
      if (!this.achievementsLoadedOnce) {
        this.lastAchievementIds = new Set(list.map(a => a.id));
        this.achievementsLoadedOnce = true;
      } else {
        // detectar solo los nuevos
        const newOnes = list.filter(a => !this.lastAchievementIds.has(a.id));
        newOnes.forEach(a => this.toast.show(a.name, a.description));
        // actualizar conjunto
        this.lastAchievementIds = new Set(list.map(a => a.id));
      }
      // actualizar lista visible
      this.achievements = list;
    });

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
