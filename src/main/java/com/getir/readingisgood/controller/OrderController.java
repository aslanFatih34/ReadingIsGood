package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.OrderCreateRequest;
import com.getir.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "/{customerId}")
    public ResponseEntity<String> createCustomer(
            @PathVariable String customerId,
            @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {
        String response = orderService.createOrder(customerId, orderCreateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
