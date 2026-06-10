import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from './models';

@Injectable({ providedIn: 'root' })
export class ProductsService {
  constructor(private http: HttpClient) {}

  /** Gateway → token relay → product-service GET /api/v1/products */
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>('/api/v1/products');
  }
}
