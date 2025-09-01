import { Component, OnInit } from '@angular/core';
import { User, UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-user-stats',
  imports: [CommonModule],
  templateUrl: './user-stats.component.html',
  styleUrl: './user-stats.component.css'
})
export class UserStatsComponent implements OnInit {
  user!: User;
  levelUp = false;
  floatingMessages: { id: number, text: string }[] = [];
  private previousLevel = 0;
  private msgCounter = 0;

  constructor(private auth: AuthService) {}

  ngOnInit(): void {
    this.auth.user$.subscribe(user => {
      if (!user) return;

      if (this.user && user.level > this.previousLevel) {
        this.levelUp = true;
        this.addFloatingMessage(`¡Nivel +${user.level - this.previousLevel}!`);
        setTimeout(() => this.levelUp = false, 1500);
      }

      this.previousLevel = user.level;
      this.user = user;
    });
  }

  // loadUser() {
  //   this.userService.getUser().subscribe(data => {
  //     // Si detecta subida de nivel, dispara la animación
  //     if (this.user && data.level > this.previousLevel) {
  //       this.levelUp = true;
  //       this.addFloatingMessage(`¡Nivel +${data.level - this.previousLevel}!`);
  //       setTimeout(() => this.levelUp = false, 1500); // dura 1.5s
  //     }
  //     this.previousLevel = data.level;
  //     this.user = data;
  //   });
  // }

  addFloatingMessage(text: string) {
    const id = this.msgCounter++;
    this.floatingMessages.push({ id, text });
    setTimeout(() => {
      this.floatingMessages = this.floatingMessages.filter(m => m.id !== id);
    }, 2000); // se elimina tras 2s
  }
}
