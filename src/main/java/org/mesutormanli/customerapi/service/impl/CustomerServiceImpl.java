package org.mesutormanli.customerapi.service.impl;

import org.mesutormanli.customerapi.model.converter.CustomerConverter;
import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.entity.CustomerEntity;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.customerapi.model.response.CustomerListResponse;
import org.mesutormanli.customerapi.repository.CustomerRepository;
import org.mesutormanli.customerapi.service.CustomerService;
import org.springframework.stereotype.Service;

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
    public CustomerListResponse getCustomer(Long id) {
        final CustomerListResponse response = new CustomerListResponse();
        return repository.findById(id)
                .map(entity -> CustomerListResponse.builder().customers(Collections.singletonList(customerConverter.toDto(entity))).build())
                .orElse(response);
    }

    @Override
    public CustomerListResponse getAllCustomers() {
        final List<CustomerEntity> entities = repository.findAll();

        final List<CustomerDto> converted = entities
                .stream()
                .map(customerConverter::toDto)
                .collect(Collectors.toList());

        return CustomerListResponse.builder().customers(converted).build();

    }

    @Override
    public CustomerDto createCustomer(CustomerRequest request) {
        final CustomerEntity saved = repository.save(customerConverter.toEntity(request));
        return customerConverter.toDto(saved);
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerRequest request) {
        final Optional<CustomerEntity> optionalCustomerEntity = repository.findById(id);
        if (!optionalCustomerEntity.isPresent()) {
            return null;
        } else {
            final CustomerEntity toBeUpdated = customerConverter.toEntity(request);
            toBeUpdated.setId(optionalCustomerEntity.get().getId());
            final CustomerEntity saved = repository.save(toBeUpdated);
            return customerConverter.toDto(saved);
        }

    }

    @Override
    public CustomerDeleteResponse deleteCustomer(Long id) {
        if (!repository.existsById(id)) {
            return CustomerDeleteResponse.builder().deletedCustomerCount(0L).build();
        } else {
            repository.deleteById(id);
            return CustomerDeleteResponse.builder().deletedCustomerCount(1L).build();
        }
    }

    @Override
    public CustomerDeleteResponse deleteAllCustomers() {
        final long count = repository.count();
        if (count == 0) {
            return CustomerDeleteResponse.builder().deletedCustomerCount(0L).build();
        } else {
            repository.deleteAll();
            return CustomerDeleteResponse.builder().deletedCustomerCount(count).build();
        }
    }

}

