package ru.khrebtov.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.khrebtov.controller.dto.AllCartDto;
import ru.khrebtov.controller.dto.Order;
import ru.khrebtov.service.OrderService;

import java.math.BigDecimal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public List<Order> findAll() {
        return orderService.getAllCartDtos();
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public List<Order> addOrder(@RequestBody AllCartDto allCartDto) {
        if (allCartDto.getSubtotal().compareTo(new BigDecimal("0.00")) <= 0) {
            return orderService.getAllCartDtos();
        }
        return orderService.addOrder(allCartDto.getSubtotal());
    }
}
