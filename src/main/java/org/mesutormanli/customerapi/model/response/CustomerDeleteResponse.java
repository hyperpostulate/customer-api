package org.mesutormanli.customerapi.model.response;

import java.io.Serializable;

public record CustomerDeleteResponse(Long deletedCustomerCount) implements Serializable {

    public CustomerDeleteResponse() {
        this(null);
    }

    public static CustomerDeleteResponseBuilder builder() {
        return new CustomerDeleteResponseBuilder();
    }

    public static class CustomerDeleteResponseBuilder {
        private Long deletedCustomerCount;

        CustomerDeleteResponseBuilder() {
        }

        public CustomerDeleteResponseBuilder deletedCustomerCount(Long deletedCustomerCount) {
            this.deletedCustomerCount = deletedCustomerCount;
            return this;
        }

        public CustomerDeleteResponse build() {
            return new CustomerDeleteResponse(deletedCustomerCount);
        }
    }
}
