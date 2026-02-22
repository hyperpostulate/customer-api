package org.mesutormanli.customerapi.model.response;

import org.mesutormanli.customerapi.model.dto.CustomerDto;

import java.io.Serializable;
import java.util.List;

public record CustomerListResponse(List<CustomerDto> customers) implements Serializable {

    public CustomerListResponse() {
        this(null);
    }

    public static CustomerListResponseBuilder builder() {
        return new CustomerListResponseBuilder();
    }

    public static class CustomerListResponseBuilder {
        private List<CustomerDto> customers;

        CustomerListResponseBuilder() {
        }

        public CustomerListResponseBuilder customers(List<CustomerDto> customers) {
            this.customers = customers;
            return this;
        }

        public CustomerListResponse build() {
            return new CustomerListResponse(customers);
        }
    }
}
