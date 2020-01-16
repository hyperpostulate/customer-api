package org.mesutormanli.customerapi.builder;

import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.customerapi.model.response.CustomerListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class CustomerMockDataBuilder {

    private CustomerMockDataBuilder() {
    }

    public static ResponseEntity<CustomerListResponse> generateCustomerListResponse(long customerId) {
        return new ResponseEntity<>(new CustomerListResponse().customers(Collections.singletonList(generateCustomerDto(customerId))), HttpStatus.OK);
    }

    public static CustomerRequest generateCustomerRequest() {
        return GenericMockDataBuilder.of(CustomerRequest.class).build();
    }

    public static ResponseEntity<CustomerDto> generateCustomerResponse(long customerId) {
        return new ResponseEntity<>(generateCustomerDto(customerId), HttpStatus.OK);
    }

    public static ResponseEntity<CustomerDeleteResponse> generateCustomerDeleteResponse() {
        return new ResponseEntity<>(new CustomerDeleteResponse().deletedCustomerCount((long) 1), HttpStatus.OK);
    }

    private static CustomerDto generateCustomerDto(long customerId) {
        return GenericMockDataBuilder.of(CustomerDto.class)
                .excludeField("id")
                .build()
                .id(customerId);
    }

}
