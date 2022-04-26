package com.getir.readingisgood.service;

import com.getir.readingisgood.dto.BookDto;
import com.getir.readingisgood.request.BookCreateRequest;

import java.util.List;

public interface BookService {
    BookDto createBook(BookCreateRequest bookCreateRequest) throws Exception;

    BookDto getBookByBookId(String bookId);

    List<BookDto> getBooks(int page, int limit);

    BookDto findByName(String name);

    void updateStock(int stock, String name);
}
