package ru.khrebtov.controller;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.khrebtov.persist.entity.Order;
import ru.khrebtov.persist.entity.User;
import ru.khrebtov.persist.repo.OrderRepository;
import ru.khrebtov.persist.repo.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    @Test
    public void testFindAllUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                            .get("/order/all")
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isUnauthorized());
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void testFindAllAuthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                            .get("/order/all")
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void testFindUsersOrder() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/order/all")
                .contentType(MediaType.APPLICATION_JSON);
        User admin = new User();
        admin.setUsername("admin");
        admin.setAge(23);
        userRepository.save(admin);
        User vasya = new User();
        vasya.setUsername("vasya");
        vasya.setAge(25);
        userRepository.save(vasya);
        Order order = new Order(1L, LocalDateTime.of(2021, 11, 04, 3, 31, 2), Order.OrderStatus.CLOSED, admin,
                                new BigDecimal("18.00"));
        orderRepository.save(order);
        Order order2 = new Order(2L, LocalDateTime.of(2021, 11, 04, 3, 31, 2), Order.OrderStatus.CLOSED, vasya,
                                 new BigDecimal("18.00"));
        orderRepository.save(order2);
        Order order3 = new Order(3L, LocalDateTime.of(2021, 11, 04, 3, 31, 2), Order.OrderStatus.CLOSED, admin,
                                 new BigDecimal("18.00"));
        orderRepository.save(order3);

        mvc.perform(requestBuilder)
           .andExpect(status().isOk())
           .andDo(print())
           .andExpect(content().string(
                   "[{\"id\":1,\"price\":18.00,\"orderDate\":\"2021-11-04T03:31:02\",\"status\":\"CLOSED\"}," +
                           "{\"id\":3,\"price\":18.00,\"orderDate\":\"2021-11-04T03:31:02\",\"status\":\"CLOSED\"}]"));
    }
}
