import { Component, inject, signal, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from '../../core/auth.service';
import { ProductsService } from '../../core/products.service';
import { Product } from '../../core/models';

@Component({
  selector: 'app-products',
  imports: [],
  templateUrl: './products.html',
})
export class Products implements OnInit {
  private productsService = inject(ProductsService);
  private auth = inject(AuthService);
  private router = inject(Router);

  readonly user = this.auth.user;
  products = signal<Product[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading.set(true);
    this.error.set(null);
    this.productsService.getProducts().subscribe({
      next: (list) => {
        this.products.set(list);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        this.loading.set(false);
        if (err.status === 401) {
          this.router.navigate(['/login']);
          return;
        }
        this.error.set('Ürünler yüklenemedi.');
      },
    });
  }

  logout(): void {
    this.auth.logout().subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => this.router.navigate(['/login']),
    });
  }
}
