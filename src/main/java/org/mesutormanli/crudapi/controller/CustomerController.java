package org.mesutormanli.crudapi.controller;

import org.mesutormanli.crudapi.model.dto.CustomerDto;
import org.mesutormanli.crudapi.model.request.CustomerRequest;
import org.mesutormanli.crudapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.crudapi.model.response.CustomerListResponse;
import org.mesutormanli.crudapi.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.mesutormanli.crudapi.controller.CustomerEndpoint.*;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(CUSTOMER_URL_WITH_ID)
    public ResponseEntity<CustomerListResponse> getCustomer(@PathVariable(ID) Long id) {
        return customerService.getCustomer(id);
    }

    @GetMapping(CUSTOMER_BASE_URL)
    public ResponseEntity<CustomerListResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping(CUSTOMER_BASE_URL)
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerRequest request) {
        return customerService.createCustomer(request);
    }

    @PutMapping(CUSTOMER_URL_WITH_ID)
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable(ID) Long id, @RequestBody CustomerRequest request) {
        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping(CUSTOMER_URL_WITH_ID)
    public ResponseEntity<CustomerDeleteResponse> deleteCustomer(@PathVariable(ID) Long id) {
        return customerService.deleteCustomer(id);
    }

    @DeleteMapping(CUSTOMER_BASE_URL)
    public ResponseEntity<CustomerDeleteResponse> deleteAllCustomers() {
        return customerService.deleteAllCustomers();
    }


}
