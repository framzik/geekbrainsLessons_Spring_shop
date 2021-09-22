package ru.khrebtov.service;

import ru.khrebtov.controller.dto.LineItem;
import ru.khrebtov.controller.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    void addProductQty(ProductDto productDto, String color, String material, int qty);

    void removeProductQty(ProductDto productDto, String color, String material, int qty);

    void removeProduct(ProductDto productDto, String color, String material);

    List<LineItem> getLineItems();

    BigDecimal getSubTotal();
}
