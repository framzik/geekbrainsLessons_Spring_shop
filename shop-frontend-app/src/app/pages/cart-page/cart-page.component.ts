import { Component, OnInit } from '@angular/core';
import { AddLineItemDto } from '../../model/add-line-item-dto';
import { AllCartDto } from '../../model/all-cart-dto';
import { LineItem } from '../../model/line-item';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';

export const CART_URL = 'cart';

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: [ './cart-page.component.scss' ]
})
export class CartPageComponent implements OnInit {

  content?: AllCartDto;

  constructor(private cartService: CartService, private orderService: OrderService) {
  }

  ngOnInit(): void {
    this.cartService.findAll().subscribe(
      res => {
        this.content = res;
      }
    );
  }

  removeFromCart(lineItem: LineItem): void {
    this.cartService.removeFromCart(lineItem.productDto.id).subscribe(
      res => {
        this.content = res;
      }
    );
  }

  updateQty(lineItem: LineItem) {
    this.cartService.updateQty(new AddLineItemDto(lineItem.productId, lineItem.qty, lineItem.color, lineItem.material))
      .subscribe(res => {
        this.content = res;
      });
  }

  clear() {
    this.cartService.clear().subscribe(
      res => {
        this.content = res;
      }
    );
  }

  createOrder(order: AllCartDto) {
    this.orderService.createOrder(order).subscribe();
    this.clear();
  }
}
