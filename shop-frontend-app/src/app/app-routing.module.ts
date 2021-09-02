import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PRODUCT_GALLERY_URL, ProductGallaryComponent } from './pages/product-gallary/product-gallary.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: PRODUCT_GALLERY_URL },
  { path: PRODUCT_GALLERY_URL, component: ProductGallaryComponent },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {
}
