package com.getir.readingisgood.service;

import com.getir.readingisgood.dto.OrderDto;
import com.getir.readingisgood.dto.StatisticDto;
import com.getir.readingisgood.model.request.OrderCreateRequest;

import java.util.List;

public interface OrderService {

    String createOrder(String customerId, OrderCreateRequest orderCreateRequest);

    OrderDto getOrderByOrderId(String bookId);

    List<OrderDto> getOrders(int page, int limit);

    List<StatisticDto> getStatistics(String customerId);
}
