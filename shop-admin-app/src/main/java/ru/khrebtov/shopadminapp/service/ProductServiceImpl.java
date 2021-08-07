package ru.khrebtov.shopadminapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.khrebtov.persist.ProductListParam;
import ru.khrebtov.persist.entity.Product;
import ru.khrebtov.persist.repo.ProductRepository;

import java.util.List;
import java.util.Optional;


import static java.util.Objects.isNull;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findWithFilter(ProductListParam productListParam) {
        Specification<Product> spec = Specification.where(null);

        if (productListParam.getMinCost() != null) {
            spec = spec.and(ProductSpecification.minCost(productListParam.getMinCost()));
        }
        if (productListParam.getMaxCost() != null) {
            spec = spec.and(ProductSpecification.maxCost(productListParam.getMaxCost()));
        }

        if (isNull(productListParam.getSortField()) || productListParam.getSortField().isEmpty() ||
                productListParam.getSortField().isBlank()) {
            productListParam.setSortField("id");
        }

        return productRepository.findAll(spec,
                                         PageRequest.of(
                                                 Optional.ofNullable(productListParam.getPage()).orElse(1) - 1,
                                                 Optional.ofNullable(productListParam.getSize()).orElse(3),
                                                 Sort.by(Optional.ofNullable(productListParam.getSortField())
                                                                 .filter(c -> !c.isBlank())
                                                                 .orElse("id"))));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
