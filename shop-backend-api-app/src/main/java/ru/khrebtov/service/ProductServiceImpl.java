package ru.khrebtov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.khrebtov.controller.dto.CategoryDto;
import ru.khrebtov.controller.dto.ProductDto;
import ru.khrebtov.persist.ProductSpecification;
import ru.khrebtov.persist.entity.Picture;
import ru.khrebtov.persist.entity.Product;
import ru.khrebtov.persist.repo.ProductRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> findAll(Optional<Long> categoryId, Optional<String> namePattern,
                                    Integer page, Integer size, String sortField) {
        Specification<Product> spec = Specification.where(null);
        if (categoryId.isPresent() && categoryId.get() != -1) {
            spec = spec.and(ProductSpecification.byCategory(categoryId.get()));
        }
        if (namePattern.isPresent()) {
            spec = spec.and(ProductSpecification.byName(namePattern.get()));
        }
        return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortField)))
                                .map(product -> new ProductDto(product.getId(),
                                                               product.getName(),
                                                               product.getDescription(),
                                                               product.getPrice(),
                                                               new CategoryDto(product.getCategory().getId(),
                                                                               product.getCategory().getName()),
                                                               product.getPictures().stream()
                                                                      .map(Picture::getId)
                                                                      .collect(Collectors.toList())));
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                                .map(product -> new ProductDto(product.getId(),
                                                               product.getName(),
                                                               product.getDescription(),
                                                               product.getPrice(),
                                                               new CategoryDto(product.getCategory().getId(),
                                                                               product.getCategory().getName()),
                                                               product.getPictures().stream()
                                                                      .map(Picture::getId)
                                                                      .collect(Collectors.toList())));
    }
}
