package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.dto.OrderDto;
import com.getir.readingisgood.dto.StatisticDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Service
@Slf4j
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
        log.info("Create order is started.CustomerId {} {} ", customerId, orderCreateRequest);
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
        Customer customerPresent;
        Book bookPresent;
        Order order;

        if (customer.isEmpty())
            throw new ApiException(ErrorMessages.CUSTOMER_COULD_NOT_FOUND);
        else
            customerPresent = customer.get();

        Optional<Book> book = bookRepository.findByName(orderCreateRequest.getBookName());
        if (book.isEmpty()) {
            throw new ApiException(ErrorMessages.BOOK_COULD_NOT_FOUND);
        } else {
            bookPresent = book.get();
            order = new Order();
            order.setOrderId(UUID.randomUUID().toString());
            order.setBook(bookPresent);
            order.setCustomer(customerPresent);
            order.setQuantity(orderCreateRequest.getQuantity());
        }

        boolean isStockEnough = bookPresent.getStock() >= orderCreateRequest.getQuantity();

        if (isStockEnough)
            updateStock(orderCreateRequest, bookPresent, order);
        else
            throw new ApiException(ErrorMessages.STOCK_IS_NOT_ENOUGH);

        orderRepository.save(order);
        log.info("Order saved: {}", order);
        return getSuccessOrderMessage(orderCreateRequest);
    }

    @Override
    public OrderDto getOrderByOrderId(String orderId) {
        log.info("getOrderByOrderId is started.OrderId {} ", orderId);
        Optional<Order> order = orderRepository.findByOrderId(orderId);

        if (order.isEmpty()) {
            throw new ApiException(ErrorMessages.ORDER_COULD_NOT_FOUND);
        } else {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order.get(), orderDto);

            log.info("getOrderByOrderId is ended.OrderId {} ", orderId);
            return orderDto;
        }
    }

    @Override
    public List<OrderDto> getOrders(int page, int limit) {
        log.info("getOrders are started.Page {} Limit {}", page, limit);

        Page<Order> pageOrders = orderRepository.findAllByOrderByCreatedDateAsc(PageRequest.of(page, limit));
        List<Order> orders = pageOrders.getContent();

        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderDto orderDto = new OrderDto();
            BeanUtils.copyProperties(order, orderDto);
            orderDto.setBookName(order.getBook().getName());
            orderDto.setCustomerName(order.getCustomer().getName());
            orderDto.setAddress(order.getCustomer().getAddress());
            orderDtos.add(orderDto);
        }

        log.info("getOrders are ended.Size {}", orderDtos.size());
        return orderDtos;
    }

    @Override
    public List<StatisticDto> getStatistics(String customerId) {
        log.info("getStatistics is started.CustomerId {}", customerId);

        List<Order> orders = orderRepository.findAllByCustomerOrderByCreatedDateAsc(customerId);
        List<StatisticDto> statisticDtos = new ArrayList<>();

        Map<Month, List<Order>> orderMap = orders
                .stream()
                .collect(groupingBy(o -> o.getCreatedDate().getMonth()));

        orderMap.forEach((key, value) -> {
            StatisticDto statisticDto = new StatisticDto(key);
            statisticDto.setTotalOrderCount(value.size());
            statisticDto.setTotalBookCount(value.stream().mapToInt(Order::getQuantity).sum());
            statisticDto.setTotalPurchasedAmount(
                    value.stream()
                            .map(Order::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add));

            statisticDtos.add(statisticDto);
        });

        statisticDtos.sort(Comparator.comparing(StatisticDto::getMonth));
        log.info("getStatistics are ended.Size {} ", statisticDtos.size());
        return statisticDtos;
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
