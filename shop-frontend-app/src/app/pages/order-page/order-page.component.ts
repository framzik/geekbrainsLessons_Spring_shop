import { Component, OnInit } from '@angular/core';
import { Order } from '../../model/order';
import { OrderStatusService } from '../../services/order-status.service';
import { OrderService } from '../../services/order.service';

export const ORDERS_URL = 'order';

@Component({
  selector: 'app-order-page',
  templateUrl: './order-page.component.html',
  styleUrls: [ './order-page.component.scss' ]
})
export class OrderPageComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrderService,
              private orderStatusService: OrderStatusService) {
  }

  ngOnInit(): void {
    this.orderService.findOrders()
      .subscribe(orders => {
          this.orders = orders;
        },
        error => {
          console.log(error);
        });
    this.orderStatusService.onMessage('/order_out/order')
      .subscribe(msg => {
        console.log(`New message with status ${msg.status}`);

        let find = this.orders.find(order => order.id == msg.id);
        if (find) {
          find.status = msg.status;
        }
      });
  }

}
