package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.OrderCreateRequest;
import com.getir.readingisgood.service.OrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(
        title = "Getir API",
        version = "v1"
))
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "/{customerId}")
    @Operation(summary = "Create order")
    public ResponseEntity<String> createOrder(
            @PathVariable String customerId,
            @Valid @RequestBody OrderCreateRequest orderCreateRequest) throws Exception {
        String response = orderService.createOrder(customerId, orderCreateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
