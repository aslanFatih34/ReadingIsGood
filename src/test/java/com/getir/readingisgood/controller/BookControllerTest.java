package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.TestInit;
import com.getir.readingisgood.dto.BookDto;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.model.request.BookCreateRequest;
import com.getir.readingisgood.repository.BookRepository;
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
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(initializers = TestInit.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @SpyBean
    BookRepository bookRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    ObjectMapper objectMapper;

    @Value("${app.security.user.name}")
    private String userName;

    @Value("${app.security.user.password}")
    private String password;

    private String bookId = UUID.randomUUID().toString();


    @BeforeEach
    void init() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        generateBooks();
    }

    @AfterEach
    void tearDown() {
        clearDatabases();
    }

    @Test
    public void getBookByBookId() {
        ResponseEntity<BookDto> responseEntity = restTemplate.withBasicAuth(userName, password).getForEntity("/book/" + bookId, BookDto.class);
        BookDto bookDto = objectMapper.convertValue(responseEntity.getBody(), BookDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bookId, bookDto.getBookId());
        assertEquals("book-name", bookDto.getName());
        assertEquals(BigDecimal.TEN, bookDto.getPrice().setScale(0));
    }

    @Test
    void getBooks() {
        ResponseEntity<List> responseEntity = restTemplate.withBasicAuth(userName, password).getForEntity("/book/", List.class);
        List<BookDto> bookDtos = (List<BookDto>) objectMapper.convertValue(responseEntity.getBody(), List.class);
        assertEquals(bookDtos.size(), 1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void createBook() {
        BookCreateRequest bookCreateRequest = BookCreateRequest.builder()
                .name("Test-Name")
                .description("Test-Desc")
                .price(BigDecimal.TEN)
                .stock(5)
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<Object> request = new HttpEntity<>(bookCreateRequest, httpHeaders);

        ResponseEntity<BookDto> responseEntity = restTemplate.withBasicAuth(userName, password).postForEntity("/book", request, BookDto.class);
        BookDto bookDto = objectMapper.convertValue(responseEntity.getBody(), BookDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Test-Name", bookDto.getName());
        assertEquals("Test-Desc", bookDto.getDescription());
        assertEquals(BigDecimal.TEN, bookDto.getPrice());
        assertEquals(5, bookDto.getStock());
    }

    private void generateBooks() {
        Book book = new Book();
        book.setBookId(bookId);
        book.setDescription("description");
        book.setName("book-name");
        book.setPrice(BigDecimal.TEN);
        book.setStock(10);
        bookRepository.save(book);
    }

    private void clearDatabases() {
        String deleteApplicationSql = "DELETE FROM book";
        jdbcTemplate.execute(deleteApplicationSql);
    }

}
