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
        return CustomerListResponse.builder()
                .customers(Collections.singletonList(generateCustomerDto(customerId)))
                .build();
    }

    public static CustomerRequest generateCustomerRequest() {
        return GenericMockDataBuilder.of(CustomerRequest.class).build();
    }

    public static CustomerDeleteResponse generateCustomerDeleteResponse() {
        return CustomerDeleteResponse.builder()
                .deletedCustomerCount(1L)
                .build();
    }

    public static CustomerDto generateCustomerDto(long customerId) {
        final CustomerDto dto = GenericMockDataBuilder.of(CustomerDto.class)
                .excludeField("id")
                .build();
        dto.setId(customerId);
        return dto;
    }

}
