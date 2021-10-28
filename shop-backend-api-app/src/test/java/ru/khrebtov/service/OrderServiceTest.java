package ru.khrebtov.service;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.khrebtov.controller.dto.*;
import ru.khrebtov.persist.entity.Order;
import ru.khrebtov.persist.entity.User;
import ru.khrebtov.persist.repo.OrderRepository;
import ru.khrebtov.persist.repo.ProductRepository;
import ru.khrebtov.persist.repo.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;
    @MockBean
    private RabbitTemplate rabbitTemplate;
    @MockBean
    private SimpMessagingTemplate template;

    @Test
    public void findOrdersByUsernameTest() {
        User vasya = new User();
        vasya.setUsername("vasya");
        vasya.setAge(23);
        userRepository.save(vasya);
        Order order = new Order(1L, LocalDateTime.now(), Order.OrderStatus.CLOSED, vasya, new BigDecimal("18.00"));
        orderRepository.save(order);

        List<OrderDto> orders = orderService.findOrdersByUsername(vasya.getUsername());
        assertFalse(orders.isEmpty());
        assertEquals(orders.get(0).getOrderDate(), order.getOrderDate());
        assertEquals(orders.get(0).getStatus(), order.getStatus());
        assertEquals(orders.get(0).getPrice(), order.getSubTotal());
    }

    @Test
    public void createOrderTest() {
        User vasya = new User();
        vasya.setUsername("vasya");
        vasya.setAge(23);
        userRepository.save(vasya);

        List<Long> pictures = new ArrayList<>();
        CategoryDto cat = new CategoryDto(1L, "Cat");
        ProductDto productDto = new ProductDto(1L, "First", "S", new BigDecimal(7),
                                               cat, pictures);

        LineItem e1 = new LineItem(productDto, "green", "gold");
        e1.setQty(10);
        List<LineItem> lineItems = List.of(e1);
        cartService = new CartServiceImpl(lineItems);

        AllCartDto cartDto = new AllCartDto(lineItems, new BigDecimal(9));
        orderService.createOrder(vasya.getUsername(), cartDto);
        List<OrderDto> ordersByUsername = orderService.findOrdersByUsername(vasya.getUsername());
        assertFalse(ordersByUsername.isEmpty());
    }
}
