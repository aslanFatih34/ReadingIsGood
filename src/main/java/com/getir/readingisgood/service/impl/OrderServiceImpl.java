package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.enums.ErrorMessages;
import com.getir.readingisgood.enums.OrderStatus;
import com.getir.readingisgood.exception.ApiException;
import com.getir.readingisgood.model.request.OrderCreateRequest;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    CustomerRepository customerRepository;
    BookRepository bookRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, BookRepository bookRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public String createOrder(String customerId, OrderCreateRequest orderCreateRequest) {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (Objects.isNull(customer))
            throw new ApiException(ErrorMessages.CUSTOMER_COULD_NOT_FOUND);

        Book book = bookRepository.findByName(orderCreateRequest.getBookName());
        if (Objects.isNull(book))
            throw new ApiException(ErrorMessages.BOOK_COULD_NOT_FOUND);

        Order order = new Order();
        order.setBook(book);
        order.setCustomer(customer);
        order.setQuantity(orderCreateRequest.getQuantity());

        boolean isStockEnough = book.getStock() >= orderCreateRequest.getQuantity();

        if (isStockEnough)
            updateStock(orderCreateRequest, book, order);
        else
            order.setStatus(OrderStatus.FAIL);

        orderRepository.save(order);
//TODO REDUNDANT
        if (!isStockEnough)
            throw new ApiException(ErrorMessages.STOCK_IS_NOT_ENOUGH);

        return getSuccessOrderMessage(orderCreateRequest);
    }

    private void updateStock(OrderCreateRequest orderCreateRequest, Book book, Order order) {
        int remainingStock = book.getStock() - orderCreateRequest.getQuantity();
        bookRepository.updateStock(remainingStock, orderCreateRequest.getBookName());

        BigDecimal price = calculatePrice(orderCreateRequest, book);
        order.setPrice(price);
        order.setStatus(OrderStatus.COMPLETED);
    }

    private String getSuccessOrderMessage(OrderCreateRequest orderCreateRequest) {
        return orderCreateRequest.getQuantity() + " " + orderCreateRequest.getBookName() + " successfully ordered.";
    }

    private BigDecimal calculatePrice(OrderCreateRequest orderCreateRequest, Book book) {
        return book.getPrice().multiply(BigDecimal.valueOf(orderCreateRequest.getQuantity()));
    }

}
