package ru.khrebtov.shopadminapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.khrebtov.persist.CategoryListParam;
import ru.khrebtov.persist.entity.Category;
import ru.khrebtov.persist.repo.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findWithFilter(CategoryListParam categoryListParam) {
        Specification<Category> spec = Specification.where(null);

        if (categoryListParam.getCategoryNameFilter() != null && !categoryListParam.getCategoryNameFilter().isBlank()) {
            spec = spec.and(CategorySpecifications.categoryPrefix(categoryListParam.getCategoryNameFilter()));
        }

        return categoryRepository.findAll(spec,
                                      PageRequest.of(
                                              Optional.ofNullable(categoryListParam.getPage()).orElse(1) - 1,
                                              Optional.ofNullable(categoryListParam.getSize()).orElse(3),
                                              Sort.by(Optional.ofNullable(categoryListParam.getSortField())
                                                              .filter(c -> !c.isBlank())
                                                              .orElse("id"))));
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
