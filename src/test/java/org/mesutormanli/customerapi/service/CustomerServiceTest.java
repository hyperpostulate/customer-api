package org.mesutormanli.customerapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mesutormanli.customerapi.base.BaseServiceTest;
import org.mesutormanli.customerapi.model.converter.CustomerReadConverter;
import org.mesutormanli.customerapi.model.converter.CustomerWriteConverter;
import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.entity.CustomerEntity;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.repository.CustomerRepository;
import org.mesutormanli.customerapi.service.impl.CustomerServiceImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mesutormanli.customerapi.builder.CustomerMockDataBuilder.generateCustomerRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CustomerServiceTest extends BaseServiceTest {

    private static final long CUSTOMER_ID = 1;
    private CustomerRequest customerRequest;
    private CustomerEntity customerEntity;
    private CustomerDto customerDto;

    @MockitoBean
    private CustomerRepository repository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        final CustomerReadConverter readConverter = new CustomerReadConverter();
        final CustomerWriteConverter writeConverter = new CustomerWriteConverter();
        customerRequest = generateCustomerRequest();
        customerEntity = writeConverter.toEntity(customerRequest);
        customerEntity.setId(CUSTOMER_ID);
        customerDto = readConverter.toDto(customerEntity);
        customerService = new CustomerServiceImpl(repository, readConverter, writeConverter);
    }

    @Test
    void getCustomer_success() {
        when(repository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customerEntity));
        final Optional<CustomerDto> response = customerService.getCustomer(CUSTOMER_ID);
        assertTrue(response.isPresent());
        assertEquals(customerDto, response.get());
    }

    @Test
    void getCustomer_notFound() {
        when(repository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
        final Optional<CustomerDto> response = customerService.getCustomer(CUSTOMER_ID);
        assertTrue(response.isEmpty());
    }

    @Test
    void getAllCustomers_success() {
        when(repository.findAll()).thenReturn(Collections.singletonList(customerEntity));
        final List<CustomerDto> response = customerService.getAllCustomers();
        assertEquals(1, response.size());
        assertEquals(customerDto, response.get(0));
    }

    @Test
    void getAllCustomers_notFound() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        final List<CustomerDto> response = customerService.getAllCustomers();
        assertTrue(response.isEmpty());
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
        final Optional<CustomerDto> response = customerService.updateCustomer(CUSTOMER_ID, customerRequest);
        assertTrue(response.isPresent());
        assertEquals(customerDto, response.get());
    }

    @Test
    void updateCustomer_notFound() {
        when(repository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
        final Optional<CustomerDto> response = customerService.updateCustomer(CUSTOMER_ID, customerRequest);
        assertTrue(response.isEmpty());
    }

    @Test
    void deleteCustomer_success() {
        when(repository.existsById(CUSTOMER_ID)).thenReturn(true);
        doNothing().when(repository).deleteById(CUSTOMER_ID);
        final long response = customerService.deleteCustomer(CUSTOMER_ID);
        assertEquals(1L, response);
    }

    @Test
    void deleteCustomer_noContent() {
        when(repository.existsById(CUSTOMER_ID)).thenReturn(false);
        final long response = customerService.deleteCustomer(CUSTOMER_ID);
        assertEquals(0L, response);
    }

    @Test
    void deleteAllCustomers_success() {
        when(repository.count()).thenReturn((long) 1);
        doNothing().when(repository).deleteAll();
        final long response = customerService.deleteAllCustomers();
        assertEquals(1L, response);
    }

    @Test
    void deleteAllCustomers_noContent() {
        when(repository.count()).thenReturn((long) 0);
        final long response = customerService.deleteAllCustomers();
        assertEquals(0L, response);
    }

}
