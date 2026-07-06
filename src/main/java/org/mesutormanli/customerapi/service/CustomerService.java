package org.mesutormanli.customerapi.service;

import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.request.CustomerRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Optional<CustomerDto> getCustomer(Long id);

    List<CustomerDto> getAllCustomers();

    CustomerDto createCustomer(CustomerRequest request);

    Optional<CustomerDto> updateCustomer(Long id, CustomerRequest request);

    long deleteCustomer(Long id);

    long deleteAllCustomers();

}
