package org.mesutormanli.customerapi.service;

import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.customerapi.model.response.CustomerListResponse;

public interface CustomerService {
    CustomerListResponse getCustomer(Long id);

    CustomerListResponse getAllCustomers();

    CustomerDto createCustomer(CustomerRequest request);

    CustomerDto updateCustomer(Long id, CustomerRequest request);

    CustomerDeleteResponse deleteCustomer(Long id);

    CustomerDeleteResponse deleteAllCustomers();

}
