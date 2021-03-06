package ru.khrebtov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.khrebtov.controller.dto.AddLineItemDto;
import ru.khrebtov.controller.dto.AllCartDto;
import ru.khrebtov.controller.dto.LineItem;
import ru.khrebtov.controller.dto.ProductDto;
import ru.khrebtov.service.CartService;
import ru.khrebtov.service.ProductService;

import java.util.List;

@RequestMapping("/cart")
@RestController
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public List<LineItem> addToCart(@RequestBody AddLineItemDto addLineItemDto) {
        logger.info("New LineItem. ProductId = {}, qty = {}", addLineItemDto.getProductId(), addLineItemDto.getQty());

        ProductDto productDto = productService.findById(addLineItemDto.getProductId())
                                              .orElseThrow(RuntimeException::new);
        cartService.addProductQty(productDto, addLineItemDto.getColor(), addLineItemDto.getMaterial(),
                                  addLineItemDto.getQty());
        return cartService.getLineItems();
    }

    @PostMapping(path = "/update_qty", produces = "application/json", consumes = "application/json")
    public AllCartDto updateQty(@RequestBody AddLineItemDto addLineItemDto) {
        logger.info("Update LineItem. ProductId = {}, qty = {}", addLineItemDto.getProductId(),
                    addLineItemDto.getQty());

        ProductDto productDto = productService.findById(addLineItemDto.getProductId())
                                              .orElseThrow(RuntimeException::new);
        cartService.removeProductQty(productDto, addLineItemDto.getColor(), addLineItemDto.getMaterial(),
                                     addLineItemDto.getQty());
        return new AllCartDto(cartService.getLineItems(), cartService.getSubTotal());
    }

    @GetMapping("/all")
    public AllCartDto findAll() {
        return new AllCartDto(cartService.getLineItems(), cartService.getSubTotal());
    }

    @DeleteMapping("/delete/{id}")
    public AllCartDto deleteProduct(@PathVariable Long id) {
        logger.info("Delete LineItem. ProductId = {}", id);

        ProductDto productDto = productService.findById(id)
                                              .orElseThrow(RuntimeException::new);
        cartService.removeProduct(productDto);

        return new AllCartDto(cartService.getLineItems(), cartService.getSubTotal());
    }

    @DeleteMapping()
    public List<LineItem> deleteProducts() {
        logger.info("Delete AllLineItem. ");

        cartService.removeAll();

        return cartService.getLineItems();
    }
}
