package org.mesutormanli.customerapi.model.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mesutormanli.customerapi.base.BaseServiceTest;
import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.entity.CustomerEntity;

import static org.junit.jupiter.api.Assertions.assertNull;

class CustomerConverterTest extends BaseServiceTest {

    private CustomerConverter customerConverter;

    @BeforeEach
    void setUp() {
        customerConverter = new CustomerConverter();
    }

    @Test
    void toDto_null() {
        final CustomerDto customerDto = customerConverter.toDto(null);
        assertNull(customerDto);
    }

    @Test
    void toEntity_null() {
        final CustomerEntity customerEntity = customerConverter.toEntity(null);
        assertNull(customerEntity);
    }

}