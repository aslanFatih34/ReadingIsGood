package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.dto.BookDto;
import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.enums.ErrorMessages;
import com.getir.readingisgood.exception.ApiException;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.request.BookCreateRequest;
import com.getir.readingisgood.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {
    private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public BookDto createBook(BookCreateRequest bookCreateRequest) {
        logger.info("createProduct is started." + bookCreateRequest);

        Book book = bookRepository.findByName(bookCreateRequest.getName());

        if (Objects.nonNull(book))
            throw new ApiException(ErrorMessages.BOOK_ALREADY_EXISTS);

        Book newBook = new Book();
        BeanUtils.copyProperties(bookCreateRequest, newBook);

        newBook.setBookId(UUID.randomUUID().toString());
        Book storedBook = bookRepository.save(newBook);

        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(storedBook, bookDto);
        logger.info("createProduct is ended." + storedBook);

        return bookDto;
    }

    @Override
    public BookDto getBookByBookId(String bookId) {
        logger.info("getBookByBookId is started.BookId " + bookId);

        Book book = bookRepository.findByBookId(bookId);

        if (Objects.isNull(book))
            throw new ApiException(ErrorMessages.BOOK_COULD_NOT_FOUND);

        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);

        logger.info("getBookByBookId is ended." + book);
        return bookDto;
    }

    @Override
    public List<BookDto> getBooks(int page, int limit) {
        logger.info("getBooks are started.Page " + page + " Limit " + limit);

        Page<Book> pageBooks = bookRepository.findAll(PageRequest.of(page, limit));
        List<Book> books = pageBooks.getContent();

        List<BookDto> bookDtos = new ArrayList<>();

        for (Book book : books) {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            bookDtos.add(bookDto);
        }

        logger.info("getBooks are ended.Size :" + bookDtos.size());
        return bookDtos;
    }

    @Override
    public BookDto findByName(String name) {
        logger.info("findByName is started.Name " + name);
        Book book = bookRepository.findByName(name);

        if (Objects.isNull(book))
            throw new ApiException(ErrorMessages.BOOK_COULD_NOT_FOUND);

        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);

        logger.info("findByName is ended." + book);
        return bookDto;
    }

    @Override
    public void updateStock(int stock, String name) {

    }
}
