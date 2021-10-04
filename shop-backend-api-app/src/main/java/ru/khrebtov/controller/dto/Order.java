package ru.khrebtov.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {

    private Long id;
    private BigDecimal price;
    private String orderDate;
    private String status;

    public Order(Long id, BigDecimal price, LocalDateTime orderDate) {
        this.id = id;
        this.price = price;
        this.orderDate = orderDate.format(DateTimeFormatter.ISO_DATE_TIME);
        this.status = "init";
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
