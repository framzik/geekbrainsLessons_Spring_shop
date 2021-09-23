package ru.khrebtov.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.khrebtov.controller.dto.LineItem;
import ru.khrebtov.controller.dto.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartServiceImpl implements CartService {

    private final Map<LineItem, Integer> lineItems = new HashMap<>();

    @Override
    public void addProductQty(ProductDto productDto, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productDto, color, material);
        lineItems.put(lineItem, lineItems.getOrDefault(lineItem, 0) + qty);
    }

    @Override
    public void removeProductQty(ProductDto productDto, String color, String material, int qty) {
        LineItem lineItem = new LineItem(productDto, color, material);
        lineItems.put(lineItem, qty);
    }

    @Override
    public void removeProduct(ProductDto productDto) {
        LineItem lineItem = null;
        for (Map.Entry<LineItem, Integer> entry: lineItems.entrySet()) {
            if (entry.getKey().getProductDto().getId().equals(productDto.getId())) {
                lineItem = entry.getKey();
            }
        }
        if (nonNull(lineItem)) {
            lineItems.remove(lineItem);
        }
    }

    @Override
    public List<LineItem> getLineItems() {
        lineItems.forEach(LineItem::setQty);
        return new ArrayList<>(lineItems.keySet());
    }

    @Override
    public BigDecimal getSubTotal() {
        lineItems.forEach(LineItem::setQty);
        return lineItems.keySet()
                        .stream().map(LineItem::getItemTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void removeAll() {
        lineItems.clear();
    }
}
