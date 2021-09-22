package ru.khrebtov.controller;

import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.khrebtov.controller.dto.ProductDto;
import ru.khrebtov.service.ProductService;

import java.util.Optional;

@RequestMapping("/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public Page<ProductDto> findAll(@RequestParam("categoryId") Optional<Long> categoryId,
                                    @RequestParam("namePattern") Optional<String> namePattern,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("sortField") Optional<String> sortField) {
        return productService.findAll(
                categoryId,
                namePattern,
                page.orElse(1) - 1,
                size.orElse(5),
                sortField.filter(fld -> !fld.isBlank()).orElse("id"));
    }

    @GetMapping("/{productId}")
    public ProductDto findById(@PathVariable("productId") Long id) throws BadHttpRequest {
        Optional<ProductDto> oProduct = productService.findById(id);
        if (oProduct.isPresent()) {
            return oProduct.get();
        }

        throw new BadHttpRequest();
    }
}
