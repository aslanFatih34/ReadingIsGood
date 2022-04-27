package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.dto.CustomerDto;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.enums.ErrorMessages;
import com.getir.readingisgood.exception.ApiException;
import com.getir.readingisgood.model.request.CustomerCreateRequest;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public CustomerDto createCustomer(CustomerCreateRequest customerCreateRequest) {
        log.info("createCustomer is started.{}", customerCreateRequest);

        Optional<Customer> customer = customerRepository.findByEmail(customerCreateRequest.getEmail());
        if (customer.isPresent()) {
            throw new ApiException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        } else {
            Customer newCustomer = new Customer();
            BeanUtils.copyProperties(customerCreateRequest, newCustomer);

            newCustomer.setCustomerId(UUID.randomUUID().toString());
            newCustomer.setEncryptedPassword(passwordEncoder.encode(customerCreateRequest.getPassword()));

            Customer storedCustomer = customerRepository.save(newCustomer);

            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(storedCustomer, customerDto);

            log.info("createCustomer is ended.{}", storedCustomer);
            return customerDto;
        }
    }

    @Override
    public CustomerDto getCustomerByCustomerId(String customerId) {
        log.info("getCustomerByCustomerId is started.CustomerId {}", customerId);
        Optional<Customer> customer = customerRepository.findByCustomerId(customerId);

        if (customer.isEmpty()) {
            throw new ApiException(ErrorMessages.CUSTOMER_COULD_NOT_FOUND);
        } else {
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customer.get(), customerDto);

            log.info("getCustomerByCustomerId is ended.{}", customer.get());
            return customerDto;
        }
    }

    @Override
    public List<CustomerDto> getCustomers(int page, int limit) {
        log.info("getCustomers is started.Page {} Limit {}", page, limit);

        Page<Customer> pageCustomers = customerRepository.findAll(PageRequest.of(page, limit));
        List<Customer> customers = pageCustomers.getContent();

        List<CustomerDto> customerDtos = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customer, customerDto);
            customerDtos.add(customerDto);
        }

        log.info("getBooks are ended.Size {} ", customerDtos.size());
        return customerDtos;
    }
}
