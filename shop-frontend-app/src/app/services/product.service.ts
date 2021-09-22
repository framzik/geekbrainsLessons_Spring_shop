import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Page } from '../model/page';
import { Product } from '../model/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private products: Product[] = [];

  constructor(private http: HttpClient) {
  }

  public findAll() {
    return this.http.get<Page>('/api/v1/product/all').toPromise();
  }
}
