package org.mesutormanli.crudapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mesutormanli.crudapi.base.BaseServiceTest;
import org.mesutormanli.crudapi.model.converter.CustomerConverter;
import org.mesutormanli.crudapi.model.dto.CustomerDto;
import org.mesutormanli.crudapi.model.entity.CustomerEntity;
import org.mesutormanli.crudapi.model.request.CustomerRequest;
import org.mesutormanli.crudapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.crudapi.model.response.CustomerListResponse;
import org.mesutormanli.crudapi.repository.CustomerRepository;
import org.mesutormanli.crudapi.service.impl.CustomerServiceImpl;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mesutormanli.crudapi.builder.CustomerMockDataBuilder.generateCustomerRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


class CustomerServiceTest extends BaseServiceTest {

    private static final long CUSTOMER_ID = 1;
    private CustomerListResponse customerListResponse;
    private CustomerRequest customerRequest;
    private CustomerEntity customerEntity;
    private CustomerDto customerDto;

    @MockBean
    private CustomerRepository repository;

    private CustomerService customerService;


    @BeforeEach
    void setUp() {
        final CustomerConverter customerConverter = new CustomerConverter();
        customerRequest = generateCustomerRequest();
        customerEntity = customerConverter.toEntity(customerRequest).id(CUSTOMER_ID);
        customerDto = customerConverter.toDto(customerEntity);
        customerListResponse = new CustomerListResponse().customers(Collections.singletonList(customerDto));
        customerService = new CustomerServiceImpl(repository, customerConverter);
    }

    @Test
    void getCustomer_success() {
        when(repository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));
        final ResponseEntity<CustomerListResponse> response = customerService.getCustomer(CUSTOMER_ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerListResponse, response.getBody());
    }

    @Test
    void getAllCustomers_success() {
        when(repository.findAll()).thenReturn(Collections.singletonList(customerEntity));
        final ResponseEntity<CustomerListResponse> response = customerService.getAllCustomers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerListResponse, response.getBody());
    }

    @Test
    void createCustomer_success() {
        when(repository.save(any())).thenReturn(customerEntity);
        final ResponseEntity<CustomerDto> response = customerService.createCustomer(customerRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerDto, response.getBody());
    }

    @Test
    void updateCustomer_success() {
        when(repository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));
        when(repository.save(any())).thenReturn(customerEntity);
        final ResponseEntity<CustomerDto> response = customerService.updateCustomer(CUSTOMER_ID, customerRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDto, response.getBody());
    }

    @Test
    void deleteCustomer_success() {
        when(repository.existsById(CUSTOMER_ID)).thenReturn(true);
        doNothing().when(repository).deleteById(CUSTOMER_ID);
        final ResponseEntity<CustomerDeleteResponse> response = customerService.deleteCustomer(CUSTOMER_ID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getDeletedCustomerCount());
    }

    @Test
    void deleteAllCustomers_success() {
        when(repository.count()).thenReturn((long) 1);
        doNothing().when(repository).deleteAll();
        final ResponseEntity<CustomerDeleteResponse> response = customerService.deleteAllCustomers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getDeletedCustomerCount());
    }
}