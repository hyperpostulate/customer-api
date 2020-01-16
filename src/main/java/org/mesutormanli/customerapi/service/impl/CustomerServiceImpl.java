package org.mesutormanli.customerapi.service.impl;

import org.mesutormanli.customerapi.model.converter.CustomerConverter;
import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.entity.CustomerEntity;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.customerapi.model.response.CustomerListResponse;
import org.mesutormanli.customerapi.repository.CustomerRepository;
import org.mesutormanli.customerapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        final List<CustomerEntity> entities = repository.findAll();
        if (CollectionUtils.isEmpty(entities)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final List<CustomerDto> converted = entities
                .stream()
                .map(customerConverter::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(new CustomerListResponse().customers(converted), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CustomerDto> createCustomer(CustomerRequest request) {
        final CustomerEntity saved = repository.save(customerConverter.toEntity(request));
        return new ResponseEntity<>(customerConverter.toDto(saved), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CustomerDto> updateCustomer(Long id, CustomerRequest request) {
        final Optional<CustomerEntity> optionalCustomerEntity = repository.findById(id);
        if (!optionalCustomerEntity.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final CustomerEntity toBeUpdated = optionalCustomerEntity.get()
                    .name(request.getName())
                    .age(request.getAge());
            final CustomerEntity saved = repository.save(toBeUpdated);
            return new ResponseEntity<>(customerConverter.toDto(saved), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<CustomerDeleteResponse> deleteCustomer(Long id) {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            repository.deleteById(id);
            return new ResponseEntity<>(new CustomerDeleteResponse().deletedCustomerCount((long) 1), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<CustomerDeleteResponse> deleteAllCustomers() {
        final long count = repository.count();
        if (count == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            repository.deleteAll();
            return new ResponseEntity<>(new CustomerDeleteResponse().deletedCustomerCount(count), HttpStatus.OK);
        }
    }

}

