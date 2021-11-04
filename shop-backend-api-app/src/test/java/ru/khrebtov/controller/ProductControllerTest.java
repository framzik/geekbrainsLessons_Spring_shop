package ru.khrebtov.controller;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.khrebtov.persist.entity.Category;
import ru.khrebtov.persist.entity.Product;
import ru.khrebtov.persist.repo.CategoryRepository;
import ru.khrebtov.persist.repo.ProductRepository;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private SimpMessagingTemplate template;

    @Test
    public void testProductDetails() throws Exception {
        Category category = categoryRepository.save(new Category(null,"Category","some"));
        Product product = productRepository.save(new Product(null, "Product", "Description", new BigDecimal(1234), category));

        mvc.perform(MockMvcRequestBuilders
                            .get("/product/all")
                            .param("categoryId", category.getId().toString())
                            .param("page", "1")
                            .param("size", "5")
                            .param("sortField", "id")
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.content", hasSize(1)))
           .andExpect(jsonPath("$.content[0].name", is(product.getName())));
    }
}
