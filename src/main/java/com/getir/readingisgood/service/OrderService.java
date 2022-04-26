package com.getir.readingisgood.service;

import com.getir.readingisgood.model.request.OrderCreateRequest;

public interface OrderService {

    String createOrder(String customerId, OrderCreateRequest orderCreateRequest);

}
