package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.BookDto;
import com.getir.readingisgood.model.request.BookCreateRequest;
import com.getir.readingisgood.service.BookService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(
        title = "Getir API",
        version = "v1"
))
public class BookController {

    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Gets list of book")
    public ResponseEntity<List<BookDto>> getBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "2") int limit) {
        List<BookDto> bookDtos = bookService.getBooks(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(bookDtos);
    }

    @GetMapping(path = "/{bookId}")
    @Operation(summary = "Get book by given uuid")
    public ResponseEntity<BookDto> getBookByBookId(@PathVariable String bookId) {
        BookDto bookDto = bookService.getBookByBookId(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @PostMapping
    @Operation(summary = "Create book")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) throws Exception {
        BookDto bookDto = bookService.createBook(bookCreateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }
}
