package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.TestInit;
import com.getir.readingisgood.dto.OrderDto;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.model.request.OrderCreateRequest;
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
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(initializers = TestInit.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

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

    ObjectMapper objectMapper;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Value("${app.security.user.name}")
    private String userName;

    @Value("${app.security.user.password}")
    private String password;

    private String customerId = UUID.randomUUID().toString();
    private String orderId = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        generateOrder();
    }

    @AfterEach
    void tearDown() {
        clearDatabases();
    }

    @Test
    void createOrder() {
        OrderCreateRequest orderCreateRequest = OrderCreateRequest.builder()
                .bookName("book-name")
                .quantity(1)
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Object> request = new HttpEntity<>(orderCreateRequest, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.withBasicAuth(userName, password).postForEntity("/order/" + customerId, request, String.class);
        String result = objectMapper.convertValue(responseEntity.getBody(), String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderCreateRequest.getQuantity() + " " + orderCreateRequest.getBookName() + " successfully ordered.", result);
    }

    @Test
    void getOrderByOrderId() {
        ResponseEntity<OrderDto> responseEntity = restTemplate.withBasicAuth(userName, password).getForEntity("/order/" + orderId, OrderDto.class);
        OrderDto orderDto = objectMapper.convertValue(responseEntity.getBody(), OrderDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderId, orderDto.getOrderId());
    }

    @Test
    void getOrders() {
        ResponseEntity<List> responseEntity = restTemplate.withBasicAuth(userName, password).getForEntity("/order/", List.class);
        assertEquals(responseEntity.getBody().size(), 1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private void generateOrder() {
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
        order.setOrderId(orderId);
        order.setBook(book);
        order.setCustomer(customer);
        order.setQuantity(3);
        order.setPrice(BigDecimal.valueOf(30));
        orderRepository.save(order);
    }

    private void clearDatabases() {
        String deleteBookSql = "DELETE FROM book";
        String deleteOrderSql = "DELETE FROM orders ";

        jdbcTemplate.execute(deleteOrderSql);
        jdbcTemplate.execute(deleteBookSql);

    }

}