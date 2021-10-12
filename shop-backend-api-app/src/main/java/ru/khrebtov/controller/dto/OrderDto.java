package ru.khrebtov.controller.dto;

import ru.khrebtov.persist.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDto {

    private Long id;
    private BigDecimal price;
    private LocalDateTime orderDate;
    private Order.OrderStatus status;

    public OrderDto() {
    }

    public OrderDto(Long id, BigDecimal price, LocalDateTime orderDate, Order.OrderStatus status) {
        this.id = id;
        this.price = price;
        this.orderDate = orderDate;
        this.status = status;
    }

    public OrderDto(Order order) {
        this(order.getId(), order.getSubTotal(), order.getOrderDate(), order.getStatus());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Order.OrderStatus getStatus() {
        return status;
    }

    public void setStatus(Order.OrderStatus status) {
        this.status = status;
    }
}
