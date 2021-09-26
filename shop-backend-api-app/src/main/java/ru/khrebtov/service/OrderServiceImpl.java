package ru.khrebtov.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.khrebtov.controller.dto.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderServiceImpl implements OrderService {

    private final List<Order> allOrderDto;

    public OrderServiceImpl() {
        this.allOrderDto = new ArrayList<>();
    }

    @JsonCreator
    public OrderServiceImpl(List<Order> allOrderDto) {
        this.allOrderDto = allOrderDto;
    }

    @Override
    public List<Order> getAllCartDtos() {
        return allOrderDto;
    }

    @Override
    public List<Order> addOrder(BigDecimal subtotal) {
        Order order = new Order(1L, subtotal, LocalDateTime.now());
        allOrderDto.add(order);
        return allOrderDto;
    }
}
