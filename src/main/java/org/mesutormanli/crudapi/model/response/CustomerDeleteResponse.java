package org.mesutormanli.crudapi.model.response;

import java.io.Serializable;
import java.util.Objects;

public class CustomerDeleteResponse implements Serializable {
    private Long deletedCustomerCount;

    public Long getDeletedCustomerCount() {
        return deletedCustomerCount;
    }

    public CustomerDeleteResponse deletedCustomerCount(Long deletedCustomerCount) {
        this.deletedCustomerCount = deletedCustomerCount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDeleteResponse that = (CustomerDeleteResponse) o;
        return Objects.equals(deletedCustomerCount, that.deletedCustomerCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deletedCustomerCount);
    }

    @Override
    public String toString() {
        return "CustomerDeleteResponse{" +
                "deletedCustomerCount=" + deletedCustomerCount +
                '}';
    }
}
