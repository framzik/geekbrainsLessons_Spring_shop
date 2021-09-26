import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AllCartDto } from '../model/all-cart-dto';
import { Order } from '../model/order';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {
  }

  public findOrdersByUser(userId: number) {
    return this.http.get<Order[]>(`/api/v1/order/${userId}/user`);
  }

  public findOrders() {
    return this.http.get<Order[]>('/api/v1/order/all');
  }

  createOrder(cart: AllCartDto) {
    return this.http.post<any>(`api/v1/order`, cart);
  }
}
