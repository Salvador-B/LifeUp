import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.component.html'
})
export class AuthComponent {
  mode: 'login' | 'register' = 'login';
  name = ''; email = ''; password = '';
  loading = false; error = '';

  constructor(private auth: AuthService) {}

  submit(){
    this.error = ''; this.loading = true;
    const done = () => this.loading = false;
    if (this.mode === 'login') {
      this.auth.login(this.email, this.password).subscribe({ next: done, error: e => { this.error = e.error?.message || 'Error de login'; done(); }});
    } else {
      this.auth.register(this.name, this.email, this.password).subscribe({ next: done, error: e => { this.error = e.error?.message || 'Error de registro'; done(); }});
    }
  }
}
