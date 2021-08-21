package ru.khrebtov.shopadminapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.khrebtov.persist.entity.Category;
import ru.khrebtov.persist.repo.CategoryRepository;
import ru.khrebtov.shopadminapp.dto.CategoryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                                 .map(category -> new CategoryDto(category.getId(),
                                                                  category.getName(),
                                                                  category.getDescription()))
                                 .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryDto> findAll(Integer page, Integer size, String sortField) {
        return categoryRepository.findAll(PageRequest.of(page, size, Sort.by(sortField)))
                                 .map(category -> new CategoryDto(category.getId(),
                                                                  category.getName(),
                                                                  category.getDescription()));
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id)
                                 .map(category -> new CategoryDto(category.getId(),
                                                                  category.getName(),
                                                                  category.getDescription()));
    }

    @Override
    public void save(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getId(), categoryDto.getName(), categoryDto.getDescription());
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
