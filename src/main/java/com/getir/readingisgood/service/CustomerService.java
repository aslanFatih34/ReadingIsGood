package com.getir.readingisgood.service;

import com.getir.readingisgood.dto.CustomerDto;
import com.getir.readingisgood.model.request.CustomerCreateRequest;

import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CustomerCreateRequest customerCreateRequest);

    CustomerDto getCustomer(String customerId);

    List<CustomerDto> getCustomers(int page, int limit);

}
