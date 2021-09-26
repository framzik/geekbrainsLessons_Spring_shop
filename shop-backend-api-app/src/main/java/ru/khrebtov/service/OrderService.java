package ru.khrebtov.service;

import ru.khrebtov.controller.dto.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    List<Order> getAllCartDtos();

    public List<Order> addOrder(BigDecimal subtotal);
}
