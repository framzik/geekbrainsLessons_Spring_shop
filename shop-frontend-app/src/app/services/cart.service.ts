import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AddLineItemDto } from '../model/add-line-item-dto';
import { AllCartDto } from '../model/all-cart-dto';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<AllCartDto> {
    return this.http.get<AllCartDto>('api/v1/cart/all');
  }

  public addToCart(dto: AddLineItemDto) {
    return this.http.post<any>('api/v1/cart', dto);
  }
}
