package ru.khrebtov.persist.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.khrebtov.persist.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query(value = "select p from Product p left join fetch p.category",
           countQuery = "select count(p) from Product p")
    Page<Product> findAll(Pageable var2);

    List<Product> findProductByNameLike(String name);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
}
