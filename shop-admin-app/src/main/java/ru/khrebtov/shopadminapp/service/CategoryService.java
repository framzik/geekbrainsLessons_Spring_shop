package ru.khrebtov.shopadminapp.service;

import org.springframework.data.domain.Page;
import ru.khrebtov.persist.CategoryListParam;
import ru.khrebtov.persist.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    Page<Category> findWithFilter(CategoryListParam categoryListParam);

    Optional<Category> findById(Long id);

    void save(Category category);

    void deleteById(Long id);
}