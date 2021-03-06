package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.CustomerDto;
import com.getir.readingisgood.model.request.CustomerCreateRequest;
import com.getir.readingisgood.service.CustomerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
@OpenAPIDefinition(info = @Info(
        title = "Getir API",
        version = "v1"
))
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @Operation(summary = "Get customer list")
    public ResponseEntity<List<CustomerDto>> getCustomers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "2") int limit) {
        log.info("getCustomers called.Page {} Limit {}", page, limit);
        List<CustomerDto> customerDtos = customerService.getCustomers(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(customerDtos);
    }

    @GetMapping(path = "/{customerId}")
    @Operation(summary = "Get customer by given uuid")
    public ResponseEntity<CustomerDto> getCustomerByCustomerId(@PathVariable String customerId) {
        log.info("getCustomerByCustomerId called.CustomerId {} ", customerId);
        CustomerDto customerDto = customerService.getCustomerByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PostMapping
    @Operation(summary = "Create customer")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerCreateRequest customerCreateRequest) {
        log.info("createCustomer called.{} ", customerCreateRequest);
        CustomerDto customerDto = customerService.createCustomer(customerCreateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }
}
