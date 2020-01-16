package org.mesutormanli.customerapi.service;

import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.customerapi.model.response.CustomerListResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<CustomerListResponse> getCustomer(Long id);

    ResponseEntity<CustomerListResponse> getAllCustomers();

    ResponseEntity<CustomerDto> createCustomer(CustomerRequest request);

    ResponseEntity<CustomerDto> updateCustomer(Long id, CustomerRequest request);

    ResponseEntity<CustomerDeleteResponse> deleteCustomer(Long id);

    ResponseEntity<CustomerDeleteResponse> deleteAllCustomers();

}