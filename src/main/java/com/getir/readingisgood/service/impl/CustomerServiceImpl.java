package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.dto.CustomerDto;
import com.getir.readingisgood.entity.Customer;
import com.getir.readingisgood.enums.ErrorMessages;
import com.getir.readingisgood.exception.ApiException;
import com.getir.readingisgood.model.request.CustomerCreateRequest;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
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
        Customer customer = customerRepository.findByEmail(customerCreateRequest.getEmail());
        if (Objects.nonNull(customer))
            throw new ApiException(ErrorMessages.EMAIL_ALREADY_EXISTS);

        Customer newCustomer = new Customer();
        BeanUtils.copyProperties(customerCreateRequest, newCustomer);

        newCustomer.setCustomerId(UUID.randomUUID().toString());
        newCustomer.setEncryptedPassword(passwordEncoder.encode(customerCreateRequest.getPassword()));

        Customer storedCustomer = customerRepository.save(newCustomer);

        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(storedCustomer, customerDto);

        return customerDto;
    }

    @Override
    public CustomerDto getCustomer(String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId);

        if (Objects.isNull(customer))
            throw new ApiException(ErrorMessages.CUSTOMER_COULD_NOT_FOUND);

        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);

        return customerDto;
    }

    @Override
    public List<CustomerDto> getCustomers(int page, int limit) {

        Page<Customer> pageCustomers = customerRepository.findAll(PageRequest.of(page, limit));
        List<Customer> customers = pageCustomers.getContent();

        List<CustomerDto> customerDtos = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customer, customerDto);
            customerDtos.add(customerDto);
        }

        return customerDtos;
    }

    @Override
    public CustomerDto findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);

        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);

        return customerDto;
    }
}
