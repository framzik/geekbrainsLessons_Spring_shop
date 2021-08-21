package ru.khrebtov.shopadminapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.khrebtov.persist.entity.Category;
import ru.khrebtov.persist.entity.Picture;
import ru.khrebtov.persist.entity.Product;
import ru.khrebtov.persist.repo.CategoryRepository;
import ru.khrebtov.persist.repo.ProductRepository;
import ru.khrebtov.service.PictureService;
import ru.khrebtov.shopadminapp.controller.NotFoundException;
import ru.khrebtov.shopadminapp.dto.CategoryDto;
import ru.khrebtov.shopadminapp.dto.ProductDto;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final PictureService pictureService;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              PictureService pictureService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.pictureService = pictureService;
    }

    @Override
    public Page<ProductDto> findAll(Integer page, Integer size, String sortField) {
        return productRepository.findAll(PageRequest.of(page, size, Sort.by(sortField)))
                                .map(product -> new ProductDto(product.getId(),
                                                               product.getName(),
                                                               product.getDescription(),
                                                               product.getPrice(),
                                                               new CategoryDto(product.getCategory().getId(),
                                                                               product.getCategory().getName())));
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                                .map(product -> new ProductDto(product.getId(),
                                                               product.getName(),
                                                               product.getDescription(),
                                                               product.getPrice(),
                                                               new CategoryDto(product.getCategory().getId(),
                                                                               product.getCategory().getName())));
    }

    @Override
    @Transactional
    public void save(ProductDto productDto) {
        Product product = (productDto.getId() != null) ? productRepository.findById(productDto.getId())
                                                                          .orElseThrow(() -> new NotFoundException(
                                                                                  "")) : new Product();
        Category category = categoryRepository.findById(productDto.getCategory().getId())
                                              .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(productDto.getName());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());

        if (productDto.getNewPictures() != null) {
            for (MultipartFile newPicture: productDto.getNewPictures()) {
                try {
                    product.getPictures().add(new Picture(null,
                                                          newPicture.getOriginalFilename(),
                                                          newPicture.getContentType(),
                                                          pictureService.createPicture(newPicture.getBytes()),
                                                          product
                    ));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
