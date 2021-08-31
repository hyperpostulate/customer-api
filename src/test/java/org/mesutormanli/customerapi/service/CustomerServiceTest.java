package org.mesutormanli.customerapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mesutormanli.customerapi.base.BaseServiceTest;
import org.mesutormanli.customerapi.model.converter.CustomerConverter;
import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.entity.CustomerEntity;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.customerapi.model.response.CustomerListResponse;
import org.mesutormanli.customerapi.repository.CustomerRepository;
import org.mesutormanli.customerapi.service.impl.CustomerServiceImpl;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mesutormanli.customerapi.builder.CustomerMockDataBuilder.generateCustomerRequest;
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
        customerEntity = customerConverter.toEntity(customerRequest);
        customerEntity.setId(CUSTOMER_ID);
        customerDto = customerConverter.toDto(customerEntity);
        customerListResponse = CustomerListResponse.builder().customers(Collections.singletonList(customerDto)).build();
        customerService = new CustomerServiceImpl(repository, customerConverter);
    }

    @Test
    void getCustomer_success() {
        when(repository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));
        final CustomerListResponse response = customerService.getCustomer(CUSTOMER_ID);
        assertEquals(customerListResponse, response);
    }

    @Test
    void getAllCustomers_success() {
        when(repository.findAll()).thenReturn(Collections.singletonList(customerEntity));
        final CustomerListResponse response = customerService.getAllCustomers();
        assertEquals(customerListResponse, response);
    }

    @Test
    void getAllCustomers_notFound() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        final CustomerListResponse response = customerService.getAllCustomers();
        assertTrue(CollectionUtils.isEmpty(response.getCustomers()));
    }

    @Test
    void createCustomer_success() {
        when(repository.save(any())).thenReturn(customerEntity);
        final CustomerDto response = customerService.createCustomer(customerRequest);
        assertEquals(customerDto, response);
    }

    @Test
    void updateCustomer_success() {
        when(repository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));
        when(repository.save(any())).thenReturn(customerEntity);
        final CustomerDto response = customerService.updateCustomer(CUSTOMER_ID, customerRequest);
        assertEquals(customerDto, response);
    }

    @Test
    void updateCustomer_notFound() {
        when(repository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
        final CustomerDto response = customerService.updateCustomer(CUSTOMER_ID, customerRequest);
        assertNull(response);
    }

    @Test
    void deleteCustomer_success() {
        when(repository.existsById(CUSTOMER_ID)).thenReturn(true);
        doNothing().when(repository).deleteById(CUSTOMER_ID);
        final CustomerDeleteResponse response = customerService.deleteCustomer(CUSTOMER_ID);
        assertNotNull(response);
        assertEquals(1, response.getDeletedCustomerCount());
    }

    @Test
    void deleteCustomer_noContent() {
        when(repository.existsById(CUSTOMER_ID)).thenReturn(false);
        final CustomerDeleteResponse response = customerService.deleteCustomer(CUSTOMER_ID);
        assertEquals(0, response.getDeletedCustomerCount());
    }

    @Test
    void deleteAllCustomers_success() {
        when(repository.count()).thenReturn((long) 1);
        doNothing().when(repository).deleteAll();
        final CustomerDeleteResponse response = customerService.deleteAllCustomers();
        assertNotNull(response);
        assertEquals(1, response.getDeletedCustomerCount());
    }

    @Test
    void deleteAllCustomers_noContent() {
        when(repository.count()).thenReturn((long) 0);
        final CustomerDeleteResponse response = customerService.deleteAllCustomers();
        assertEquals(0, response.getDeletedCustomerCount());
    }

}
