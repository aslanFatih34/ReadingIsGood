package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.TestInit;
import com.getir.readingisgood.dto.CustomerDto;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.model.request.CustomerCreateRequest;
import com.getir.readingisgood.repository.CustomerRepository;
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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(initializers = TestInit.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @SpyBean
    CustomerRepository customerRepository;

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

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        generateCustomer();
    }

    @AfterEach
    void tearDown() {
        clearDatabases();
    }

    private void generateCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setName("Test-Name1");
        customer.setLastName("Test-LastName1");
        customer.setEmail("Test-Email1@gmail.com");
        customer.setAddress("Test-Address1");
        customer.setEncryptedPassword(passwordEncoder.encode("Test-Password"));

        customerRepository.save(customer);
    }

    @Test
    void getCustomers() {
        ResponseEntity<List> responseEntity = restTemplate.withBasicAuth(userName, password).getForEntity("/customer/", List.class);
        assertEquals(responseEntity.getBody().size(), 1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getCustomerByCustomerId() {
        ResponseEntity<CustomerDto> responseEntity = restTemplate.withBasicAuth(userName, password).getForEntity("/customer/" + customerId, CustomerDto.class);
        CustomerDto customerDto = objectMapper.convertValue(responseEntity.getBody(), CustomerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerId, customerDto.getCustomerId());
        assertEquals("Test-Name1", customerDto.getName());
        assertEquals("Test-LastName1", customerDto.getLastName());
        assertEquals("Test-Email1@gmail.com", customerDto.getEmail());
        assertEquals("Test-Address1", customerDto.getAddress());
    }

    @Test
    void createCustomer() {
        CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                .name("Test-Name")
                .lastName("Test-LastName")
                .email("Test-Email@gmail.com")
                .password("Test-Pass")
                .address("Test-Address")
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Object> request = new HttpEntity<>(customerCreateRequest, httpHeaders);

        ResponseEntity<CustomerDto> responseEntity = restTemplate.withBasicAuth(userName, password).postForEntity("/customer/", request, CustomerDto.class);
        CustomerDto customerDto = objectMapper.convertValue(responseEntity.getBody(), CustomerDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Test-Name", customerDto.getName());
        assertEquals("Test-LastName", customerDto.getLastName());
        assertEquals("Test-Email@gmail.com", customerDto.getEmail());
        assertEquals("Test-Address", customerDto.getAddress());
    }

    private void clearDatabases() {
        String deleteApplicationSql = "DELETE FROM customer";
        jdbcTemplate.execute(deleteApplicationSql);
    }
}