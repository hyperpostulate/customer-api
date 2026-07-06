package org.mesutormanli.customerapi.controller;

import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.customerapi.model.response.CustomerListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
final class CustomerResponseMapper {

    ResponseEntity<CustomerListResponse> toGetResponse(Optional<CustomerDto> customerDto) {
        return customerDto
                .map(dto -> ResponseEntity.ok(CustomerListResponse.builder()
                        .customers(Collections.singletonList(dto))
                        .build()))
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<CustomerListResponse> toGetAllResponse(List<CustomerDto> customers) {
        return ResponseEntity.ok(CustomerListResponse.builder()
                .customers(customers)
                .build());
    }

    ResponseEntity<CustomerDto> toCreateResponse(CustomerDto customerDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDto);
    }

    ResponseEntity<CustomerDto> toUpdateResponse(Optional<CustomerDto> customerDto) {
        return customerDto
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    ResponseEntity<CustomerDeleteResponse> toDeleteResponse(long deletedCount) {
        if (deletedCount == 0L) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CustomerDeleteResponse.builder()
                .deletedCustomerCount(deletedCount)
                .build());
    }

    ResponseEntity<CustomerDeleteResponse> toDeleteAllResponse(long deletedCount) {
        return toDeleteResponse(deletedCount);
    }

}
