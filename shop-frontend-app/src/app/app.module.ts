import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FooterComponent } from './components/footer/footer.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { PaginationComponent } from './components/pagination/pagination.component';
import { ProductFilterComponent } from './components/product-filter/product-filter.component';
import { ProductGalleryComponent } from './components/product-gallery/product-gallery.component';
import { UnauthorizedInterceptor } from './helpers/unauthorized-interceptor';
import { CartPageComponent } from './pages/cart-page/cart-page.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { OrderPageComponent } from './pages/order-page/order-page.component';
import { ProductGalleryPageComponent } from './pages/product-gallery-page/product-gallery-page.component';
import { ProductInfoPageComponent } from './pages/product-info-page/product-info-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    FooterComponent,
    ProductGalleryComponent,
    ProductGalleryPageComponent,
    ProductFilterComponent,
    ProductInfoPageComponent,
    CartPageComponent,
    LoginPageComponent,
    RegisterPageComponent,
    OrderPageComponent,
    PaginationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({ cookieName: 'XSRF-TOKEN' })
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: UnauthorizedInterceptor, multi: true }
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule {
}
