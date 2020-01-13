package org.mesutormanli.crudapi.service;

import org.mesutormanli.crudapi.model.dto.CustomerDto;
import org.mesutormanli.crudapi.model.response.CustomerListResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<CustomerListResponse> getCustomerById(Long id);

    ResponseEntity<Long> createCustomer(CustomerDto dto);

    ResponseEntity<CustomerListResponse> getAllCustomers();
}