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
                .map(entity -> response.customers(Collections.singletonList(customerConverter.toDto(entity))))
                .orElse(response);
    }

    @Override
    public CustomerListResponse getAllCustomers() {
        final CustomerListResponse response = new CustomerListResponse();
        final List<CustomerEntity> entities = repository.findAll();

        final List<CustomerDto> converted = entities
                .stream()
                .map(customerConverter::toDto)
                .collect(Collectors.toList());

        return response.customers(converted);

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
            final CustomerEntity toBeUpdated = optionalCustomerEntity.get()
                    .name(request.getName())
                    .age(request.getAge());
            final CustomerEntity saved = repository.save(toBeUpdated);
            return customerConverter.toDto(saved);
        }

    }

    @Override
    public CustomerDeleteResponse deleteCustomer(Long id) {
        final CustomerDeleteResponse response = new CustomerDeleteResponse();
        if (!repository.existsById(id)) {
            return response.deletedCustomerCount(0L);
        } else {
            repository.deleteById(id);
            return response.deletedCustomerCount(1L);
        }
    }

    @Override
    public CustomerDeleteResponse deleteAllCustomers() {
        final CustomerDeleteResponse response = new CustomerDeleteResponse();
        final long count = repository.count();
        if (count == 0) {
            return response.deletedCustomerCount(0L);
        } else {
            repository.deleteAll();
            return response.deletedCustomerCount(count);
        }
    }

}

