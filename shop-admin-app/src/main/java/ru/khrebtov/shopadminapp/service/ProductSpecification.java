package ru.khrebtov.shopadminapp.service;

import org.springframework.data.jpa.domain.Specification;
import ru.khrebtov.persist.entity.Product;

public class ProductSpecification {

    public static Specification<Product> minCost(Integer minCost) {
        return (root, query, builder) -> builder.ge(root.get("cost"), minCost);
    }

    public static Specification<Product> maxCost(Integer maxCost) {
        return (root, query, builder) -> builder.le(root.get("cost"), maxCost);
    }
}
