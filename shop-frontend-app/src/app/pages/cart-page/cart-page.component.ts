import { Component, OnInit } from '@angular/core';
import { AddLineItemDto } from '../../model/add-line-item-dto';
import { AllCartDto } from '../../model/all-cart-dto';
import { LineItem } from '../../model/line-item';
import { CartService } from '../../services/cart.service';

export const CART_URL = 'cart';

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: [ './cart-page.component.scss' ]
})
export class CartPageComponent implements OnInit {

  content?: AllCartDto;

  constructor(private cartService: CartService) {
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
    this.cartService.updateQty(new AddLineItemDto(lineItem.productId, 2, lineItem.color, lineItem.material))
      .subscribe();
  }

  clear() {
    this.cartService.clear().subscribe();
  }
}
