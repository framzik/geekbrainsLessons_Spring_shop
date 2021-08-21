package ru.khrebtov.shopadminapp.service;

import org.springframework.data.domain.Page;
import ru.khrebtov.shopadminapp.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto> findAll();

    Page<CategoryDto> findAll(Integer page, Integer size, String sortField);

    Optional<CategoryDto> findById(Long id);

    void save(CategoryDto categoryDto);

    void deleteById(Long id);
}