package org.mesutormanli.crudapi.service.impl;

import org.mesutormanli.crudapi.model.converter.CustomerConverter;
import org.mesutormanli.crudapi.model.dto.CustomerDto;
import org.mesutormanli.crudapi.model.entity.CustomerEntity;
import org.mesutormanli.crudapi.model.request.CustomerRequest;
import org.mesutormanli.crudapi.model.response.CustomerListResponse;
import org.mesutormanli.crudapi.repository.CustomerRepository;
import org.mesutormanli.crudapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
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
    public ResponseEntity<CustomerListResponse> getCustomer(Long id) {
        return repository.findById(id)
                .map(entity -> new ResponseEntity<>(new CustomerListResponse().customers(Collections.singletonList(customerConverter.toDto(entity))), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<CustomerDto> createCustomer(CustomerRequest request) {
        CustomerEntity saved = repository.save(customerConverter.toEntity(request));
        return new ResponseEntity<>(customerConverter.toDto(saved), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        List<CustomerEntity> entities = repository.findAll();
        if (CollectionUtils.isEmpty(entities)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<CustomerDto> converted = entities
                .stream()
                .map(customerConverter::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new CustomerListResponse().customers(converted), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CustomerDto> updateCustomer(Long id, CustomerRequest request) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            CustomerEntity toBeUpdated = repository.findById(id).get()
                    .name(request.getName())
                    .age(request.getAge());
            CustomerEntity saved = repository.save(toBeUpdated);
            return new ResponseEntity<>(customerConverter.toDto(saved), HttpStatus.OK);
        }

    }

}

