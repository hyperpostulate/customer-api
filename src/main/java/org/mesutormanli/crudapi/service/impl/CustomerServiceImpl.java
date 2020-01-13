package org.mesutormanli.crudapi.service.impl;

import org.mesutormanli.crudapi.model.converter.CustomerEntityConverter;
import org.mesutormanli.crudapi.model.dto.CustomerDto;
import org.mesutormanli.crudapi.model.response.CustomerListResponse;
import org.mesutormanli.crudapi.repository.CustomerRepository;
import org.mesutormanli.crudapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerEntityConverter customerEntityConverter;

    public CustomerServiceImpl(CustomerRepository repository, CustomerEntityConverter customerEntityConverter) {
        this.repository = repository;
        this.customerEntityConverter = customerEntityConverter;
    }


    @Override
    public ResponseEntity<CustomerListResponse> getCustomerById(Long id) {
        return repository.findById(id)
                .map(entity -> new ResponseEntity<>(new CustomerListResponse().customers(Collections.singletonList(customerEntityConverter.toDto(entity))), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Long> createCustomer(CustomerDto dto) {
        return new ResponseEntity<>(repository.save(customerEntityConverter.toEntity(dto)).getId(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        return new ResponseEntity<>(new CustomerListResponse().customers(repository.findAll()
                .stream()
                .map(customerEntityConverter::toDto)
                .collect(Collectors.toList())), HttpStatus.OK);

    }
}

