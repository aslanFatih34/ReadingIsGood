package com.getir.readingisgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.readingisgood.TestInit;
import com.getir.readingisgood.dto.BookDto;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.repository.BookRepository;
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
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
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
        //TODO: clear database ekle
    }

    @Test
    public void getBooksReturnBookListWhenRequestIsValid() {
        //Arrange

        //Act
        ResponseEntity<BookDto> responseEntity = restTemplate.withBasicAuth(userName, password).getForEntity("/book/" + bookId, BookDto.class);
        BookDto bookDto = objectMapper.convertValue(responseEntity.getBody(), BookDto.class);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bookId, bookDto.getBookId());
        assertEquals("book-name", bookDto.getName());
        assertEquals(BigDecimal.TEN, bookDto.getPrice().setScale(0));
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

}
