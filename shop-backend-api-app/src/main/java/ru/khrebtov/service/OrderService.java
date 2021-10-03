package ru.khrebtov.service;

import ru.khrebtov.controller.dto.AllCartDto;
import ru.khrebtov.controller.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> findOrdersByUsername(String username);

    void createOrder(String username, AllCartDto allCartDto);
}
