package ru.khrebtov.shopadminapp.service;

import org.springframework.data.jpa.domain.Specification;
import ru.khrebtov.persist.entity.User;

public final class UserSpecifications {

    public static Specification<User> usernamePrefix(String prefix) {
        return (root, query, builder) -> builder.like(root.get("username"), prefix + "%");
    }

    public static Specification<User> minAge(Integer minAge) {
        return (root, query, builder) -> builder.ge(root.get("age"), minAge);
    }

    public static Specification<User> maxAge(Integer maxAge) {
        return (root, query, builder) -> builder.le(root.get("age"), maxAge);
    }
}
