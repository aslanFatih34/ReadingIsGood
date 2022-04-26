package com.getir.readingisgood.controller;

import com.getir.readingisgood.request.OrderCreateRequest;
import com.getir.readingisgood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

    OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "/{customerId}")
    public ResponseEntity<String> createCustomer(
            @PathVariable String customerId,
            @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {
        String response = orderService.createOrder(customerId, orderCreateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}