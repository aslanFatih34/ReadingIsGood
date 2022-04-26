package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.BookDto;
import com.getir.readingisgood.request.BookCreateRequest;
import com.getir.readingisgood.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "2") int limit) {
        List<BookDto> bookDtos = bookService.getBooks(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
    }

    @GetMapping(path = "/{bookId}")
    public ResponseEntity<BookDto> getBookByBookId(@PathVariable String bookId) {
        BookDto bookDto = bookService.getBookByBookId(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) throws Exception {
        BookDto bookDto = bookService.createBook(bookCreateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }
}
