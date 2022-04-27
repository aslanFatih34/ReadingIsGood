package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.TestInit;
import com.getir.readingisgood.dto.StatisticDto;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(initializers = TestInit.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatisticsControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @SpyBean
    BookRepository bookRepository;

    @SpyBean
    CustomerRepository customerRepository;
    @SpyBean
    OrderRepository orderRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    ObjectMapper objectMapper;

    @Value("${app.security.user.name}")
    private String userName;

    @Value("${app.security.user.password}")
    private String password;

    private String customerId = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        generateData();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getStatistics() {
        ResponseEntity<List> responseEntity = restTemplate.withBasicAuth(userName, password).getForEntity("/statistics/" + customerId, List.class);
        List<StatisticDto> statisticDtos = objectMapper.convertValue(responseEntity.getBody(), List.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(statisticDtos.size(), 1);
    }

    private void generateData() {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName("fatih");
        customer.setLastName("aslan");
        customer.setEmail("fatihaslan3427@gmail.com");
        customer.setAddress("Kartal");

        customer.setEncryptedPassword(passwordEncoder.encode("fth135"));

        customerRepository.save(customer);

        Book book = new Book();
        book.setBookId(UUID.randomUUID().toString());
        book.setDescription("description");
        book.setName("book-name");
        book.setPrice(BigDecimal.TEN);
        book.setStock(10);

        bookRepository.save(book);

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setBook(book);
        order.setCustomer(customer);
        order.setQuantity(3);
        order.setPrice(BigDecimal.valueOf(30));
        orderRepository.save(order);
    }
}