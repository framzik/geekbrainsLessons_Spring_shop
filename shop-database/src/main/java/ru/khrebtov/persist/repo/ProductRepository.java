package ru.khrebtov.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khrebtov.persist.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("select p " +
            "from Product p " +
            "where (p.cost>:minCost or :minCost is null) and" +
            "(p.cost<:maxCost or :maxCost is null )")
    List<Product> filterProduct(@Param("minCost") Integer minCost,
                                @Param("maxCost") Integer maxCost);
}
