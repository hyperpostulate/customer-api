package org.mesutormanli.customerapi.builder;

import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.customerapi.model.response.CustomerListResponse;

import java.util.Collections;

public class CustomerMockDataBuilder {

    private CustomerMockDataBuilder() {
    }

    public static CustomerListResponse generateCustomerListResponse(long customerId) {
        return new CustomerListResponse().customers(Collections.singletonList(generateCustomerDto(customerId)));
    }

    public static CustomerRequest generateCustomerRequest() {
        return GenericMockDataBuilder.of(CustomerRequest.class).build();
    }

    public static CustomerDeleteResponse generateCustomerDeleteResponse() {
        return new CustomerDeleteResponse().deletedCustomerCount((long) 1);
    }

    public static CustomerDto generateCustomerDto(long customerId) {
        return GenericMockDataBuilder.of(CustomerDto.class)
                .excludeField("id")
                .build()
                .id(customerId);
    }

}
