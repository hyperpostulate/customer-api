package org.mesutormanli.customerapi.model.response;

import org.junit.jupiter.api.Test;
import org.mesutormanli.customerapi.model.dto.CustomerDto;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CustomerListResponseTest {

    @Test
    void testNoArgConstructor() {
        CustomerListResponse response = new CustomerListResponse();
        assertNull(response.customers());
    }

    @Test
    void testBuilder() {
        CustomerDto dto = CustomerDto.builder().id(1L).name("John").build();
        List<CustomerDto> list = List.of(dto);

        CustomerListResponse response = CustomerListResponse.builder()
                .customers(list)
                .build();

        assertNotNull(response.customers());
        assertEquals(1, response.customers().size());
        assertEquals(1L, response.customers().get(0).id());
        assertEquals("John", response.customers().get(0).name());
    }
}
