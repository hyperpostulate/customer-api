package org.mesutormanli.crudapi.controller;

import org.mesutormanli.crudapi.model.dto.CustomerDto;
import org.mesutormanli.crudapi.model.response.CustomerListResponse;
import org.mesutormanli.crudapi.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private static final String CUSTOMER_BASE_URL = "/customer";

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(CUSTOMER_BASE_URL + "/{id}")
    public ResponseEntity<CustomerListResponse> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping(CUSTOMER_BASE_URL)
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping(CUSTOMER_BASE_URL)
    public ResponseEntity<Long> createCustomer(@RequestBody CustomerDto dto) {
        return customerService.createCustomer(dto);
    }
}
