package org.mesutormanli.crudapi.service.impl;

import org.mesutormanli.crudapi.model.converter.CustomerConverter;
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
    private final CustomerConverter customerConverter;

    public CustomerServiceImpl(CustomerRepository repository, CustomerConverter customerConverter) {
        this.repository = repository;
        this.customerConverter = customerConverter;
    }


    @Override
    public ResponseEntity<CustomerListResponse> getCustomerById(Long id) {
        return repository.findById(id)
                .map(entity -> new ResponseEntity<>(new CustomerListResponse().customers(Collections.singletonList(customerConverter.toDto(entity))), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<Long> createCustomer(CustomerDto dto) {
        return new ResponseEntity<>(repository.save(customerConverter.toEntity(dto)).getId(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        return new ResponseEntity<>(new CustomerListResponse().customers(repository.findAll()
                .stream()
                .map(customerConverter::toDto)
                .collect(Collectors.toList())), HttpStatus.OK);

    }

}

