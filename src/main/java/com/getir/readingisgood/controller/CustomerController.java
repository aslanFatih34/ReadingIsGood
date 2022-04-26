package com.getir.readingisgood.controller;

import com.getir.readingisgood.dto.CustomerDto;
import com.getir.readingisgood.model.request.CustomerCreateRequest;
import com.getir.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getCustomers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "2") int limit) {
        List<CustomerDto> customerDtos = customerService.getCustomers(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(customerDtos);
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerByCustomerId(@PathVariable String customerId) {
        CustomerDto customerDto = customerService.getCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerCreateRequest customerCreateRequest) {
        CustomerDto customerDto = customerService.createCustomer(customerCreateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }
}
