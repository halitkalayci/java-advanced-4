import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { User } from './models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  /** Mevcut kullanıcı (login ise dolu, değilse null). */
  readonly user = signal<User | null>(null);

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<User> {
    return this.http
      .post<User>('/bff/login', { username, password })
      .pipe(tap((u) => this.user.set(u)));
  }

  register(payload: {
    username: string;
    email: string;
    password: string;
    firstName?: string;
    lastName?: string;
  }): Observable<void> {
    return this.http.post<void>('/bff/register', payload);
  }

  /** Session'dan mevcut kullanıcıyı çeker (guard ve sayfa yenilemede). */
  me(): Observable<User> {
    return this.http.get<User>('/bff/me').pipe(tap((u) => this.user.set(u)));
  }

  logout(): Observable<void> {
    return this.http
      .post<void>('/bff/logout', {})
      .pipe(tap(() => this.user.set(null)));
  }
}
