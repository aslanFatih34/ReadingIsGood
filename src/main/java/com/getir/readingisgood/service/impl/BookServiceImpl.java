package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.dto.BookDto;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.enums.ErrorMessages;
import com.getir.readingisgood.exception.ApiException;
import com.getir.readingisgood.model.request.BookCreateRequest;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookDto createBook(BookCreateRequest bookCreateRequest) {
        log.info("createBook is started.{}", bookCreateRequest);

        Optional<Book> book = bookRepository.findByName(bookCreateRequest.getName());

        if (book.isPresent()) {
            throw new ApiException(ErrorMessages.BOOK_ALREADY_EXISTS);
        } else {
            Book newBook = new Book();
            BeanUtils.copyProperties(bookCreateRequest, newBook);

            newBook.setBookId(UUID.randomUUID().toString());
            Book storedBook = bookRepository.save(newBook);

            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(storedBook, bookDto);
            log.info("createBook is ended.{}", storedBook);

            return bookDto;
        }
    }

    @Override
    public BookDto getBookByBookId(String bookId) {
        log.info("getBookByBookId is started.BookId {}", bookId);

        Optional<Book> book = bookRepository.findByBookId(bookId);

        if (book.isEmpty()) {
            throw new ApiException(ErrorMessages.BOOK_COULD_NOT_FOUND);
        } else {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book.get(), bookDto);

            log.info("getBookByBookId is ended.{}", book.get());
            return bookDto;
        }
    }

    @Override
    public List<BookDto> getBooks(int page, int limit) {
        log.info("getBooks is started.Page {} Limit {}", page, limit);

        Page<Book> pageBooks = bookRepository.findAll(PageRequest.of(page, limit));
        List<Book> books = pageBooks.getContent();

        List<BookDto> bookDtos = new ArrayList<>();

        for (Book book : books) {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            bookDtos.add(bookDto);
        }

        log.info("getBooks are ended.Size {} ", bookDtos.size());
        return bookDtos;
    }
}
