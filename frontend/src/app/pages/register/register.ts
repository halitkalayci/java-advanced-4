import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-register',
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
})
export class Register {
  private auth = inject(AuthService);
  private router = inject(Router);

  username = '';
  email = '';
  password = '';
  firstName = '';
  lastName = '';

  loading = signal(false);
  error = signal<string | null>(null);

  submit(): void {
    this.error.set(null);
    this.loading.set(true);
    this.auth
      .register({
        username: this.username,
        email: this.email,
        password: this.password,
        firstName: this.firstName,
        lastName: this.lastName,
      })
      .subscribe({
        next: () => {
          // Kayıttan sonra otomatik giriş yapıp ürünlere geç.
          this.auth.login(this.username, this.password).subscribe({
            next: () => {
              this.loading.set(false);
              this.router.navigate(['/products']);
            },
            error: () => {
              this.loading.set(false);
              this.router.navigate(['/login']);
            },
          });
        },
        error: (err: HttpErrorResponse) => {
          this.loading.set(false);
          this.error.set(
            err.status === 409
              ? 'Bu kullanıcı adı veya e-posta zaten kayıtlı.'
              : 'Kayıt sırasında bir hata oluştu.',
          );
        },
      });
  }
}
