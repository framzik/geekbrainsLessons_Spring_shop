package ru.khrebtov.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.khrebtov.persist.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o " +
            "from Order o " +
            "inner join o.user u " +
            "where u.username = :username")
    List<Order> findAllByUsername(String username);
}
