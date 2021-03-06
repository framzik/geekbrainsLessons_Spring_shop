package ru.khrebtov.persist;

import org.springframework.data.jpa.domain.Specification;
import ru.khrebtov.persist.entity.Product;

public class ProductSpecification {

    public static Specification<Product> byCategory(long categoryId) {
        return (root, query, builder) -> builder.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> byName(String pattern) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + pattern + "%");
    }
}
