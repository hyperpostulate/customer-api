package org.mesutormanli.customerapi.model.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerRequestTest {

    @Test
    void testNoArgConstructor() {
        CustomerRequest request = new CustomerRequest();
        assertNull(request.name());
        assertNull(request.surname());
        assertNull(request.age());
        assertNull(request.address());
        assertNull(request.telephone());
        assertNull(request.email());
        assertNull(request.nationality());
        assertNull(request.maritalStatus());
    }

    @Test
    void testBuilder() {
        CustomerRequest request = CustomerRequest.builder()
                .name("John")
                .surname("Doe")
                .age(30)
                .address("123 Main St")
                .telephone("555-1234")
                .email("john@example.com")
                .nationality("US")
                .maritalStatus("Single")
                .build();

        assertEquals("John", request.name());
        assertEquals("Doe", request.surname());
        assertEquals(30, request.age());
        assertEquals("123 Main St", request.address());
        assertEquals("555-1234", request.telephone());
        assertEquals("john@example.com", request.email());
        assertEquals("US", request.nationality());
        assertEquals("Single", request.maritalStatus());
    }
}
