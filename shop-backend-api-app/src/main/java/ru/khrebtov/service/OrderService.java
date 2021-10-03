package ru.khrebtov.service;

import ru.khrebtov.persist.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> findOrdersByUsername(String username);

    void createOrder(String username);
}
