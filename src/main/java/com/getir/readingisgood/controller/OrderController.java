package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.OrderDto;
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
import java.util.List;

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
            @Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        String response = orderService.createOrder(customerId, orderCreateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/{orderId}")
    @Operation(summary = "Get order by given uuid")
    public ResponseEntity<OrderDto> getOrderByOrderId(@PathVariable String orderId) {
        OrderDto orderDto = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping
    @Operation(summary = "Gets list of orders")
    public ResponseEntity<List<OrderDto>> getOrders(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit) {
        List<OrderDto> orderDtos = orderService.getOrders(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }
}
