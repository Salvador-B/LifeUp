import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

export interface Achievement {
  id: string;
  name: string;
  description: string;
  unlockedAt: string;
}

@Component({
  selector: 'app-achievement-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './achievement-list.component.html'
})
export class AchievementListComponent {
  @Input() achievements: Achievement[] = [];
}
