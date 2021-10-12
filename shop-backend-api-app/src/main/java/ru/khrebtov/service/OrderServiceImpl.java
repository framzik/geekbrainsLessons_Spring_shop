package ru.khrebtov.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.khrebtov.controller.dto.AllCartDto;
import ru.khrebtov.controller.dto.OrderDto;
import ru.khrebtov.persist.entity.Order;
import ru.khrebtov.persist.entity.OrderLineItem;
import ru.khrebtov.persist.entity.Product;
import ru.khrebtov.persist.entity.User;
import ru.khrebtov.persist.repo.OrderRepository;
import ru.khrebtov.persist.repo.ProductRepository;
import ru.khrebtov.persist.repo.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final CartService cartService;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CartService cartService,
                            UserRepository userRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDto> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username)
                              .stream()
                              .map(OrderDto::new)
                              .collect(toList());
    }

    @Transactional
    @Override
    public void createOrder(String username, AllCartDto allCartDto) {
        if (cartService.getLineItems().isEmpty()) {
            logger.info("Can't create order for empty Cart");
            return;
        }

        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.save(new Order(
                null,
                LocalDateTime.now(),
                Order.OrderStatus.CREATED,
                user,
                allCartDto.getSubtotal()
        ));

        List<OrderLineItem> orderLineItems = cartService.getLineItems()
                                                        .stream()
                                                        .map(li -> new OrderLineItem(
                                                                null,
                                                                order,
                                                                findProductById(li.getProductId()),
                                                                li.getProductDto().getPrice(),
                                                                li.getQty(),
                                                                li.getColor(),
                                                                li.getMaterial()
                                                        ))
                                                        .collect(toList());
        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("No product with id"));
    }
}
