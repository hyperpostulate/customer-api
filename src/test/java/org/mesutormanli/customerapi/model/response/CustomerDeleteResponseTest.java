package org.mesutormanli.customerapi.model.response;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerDeleteResponseTest {

    @Test
    void testNoArgConstructor() {
        CustomerDeleteResponse response = new CustomerDeleteResponse();
        assertNull(response.deletedCustomerCount());
    }

    @Test
    void testBuilder() {
        CustomerDeleteResponse response = CustomerDeleteResponse.builder()
                .deletedCustomerCount(5L)
                .build();

        assertEquals(5L, response.deletedCustomerCount());
    }
}
