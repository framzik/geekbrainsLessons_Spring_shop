package ru.khrebtov.shopadminapp.service;

import org.springframework.data.jpa.domain.Specification;
import ru.khrebtov.persist.entity.Category;

public class CategorySpecifications {
    public static Specification<Category> categoryPrefix(String prefix) {
        return (root, query, builder) -> builder.like(root.get("name"), prefix + "%");
    }
}
