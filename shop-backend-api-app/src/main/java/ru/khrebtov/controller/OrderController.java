package ru.khrebtov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.khrebtov.controller.dto.AllCartDto;
import ru.khrebtov.controller.dto.OrderDto;
import ru.khrebtov.service.OrderService;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(Authentication auth, @RequestBody AllCartDto allCartDto) {
        orderService.createOrder(auth.getName(), allCartDto);
    }

    @GetMapping("/all")
    public List<OrderDto> findAll(Authentication auth) {
        return orderService.findOrdersByUsername(auth.getName());
    }
}
