package org.mesutormanli.customerapi.model.response;

import org.mesutormanli.customerapi.model.dto.CustomerDto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class CustomerListResponse implements Serializable {
    private List<CustomerDto> customers;

    public List<CustomerDto> getCustomers() {
        return customers;
    }

    public CustomerListResponse customers(List<CustomerDto> customers) {
        this.customers = customers;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerListResponse that = (CustomerListResponse) o;
        return Objects.equals(customers, that.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customers);
    }

    @Override
    public String toString() {
        return "CustomerListResponse{" +
                "customers=" + customers +
                '}';
    }
}
