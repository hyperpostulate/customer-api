package org.mesutormanli.customerapi.model.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mesutormanli.customerapi.base.BaseServiceTest;
import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.entity.CustomerEntity;

import static org.junit.jupiter.api.Assertions.assertNull;

class CustomerConverterTest extends BaseServiceTest {

    private CustomerReadConverter readConverter;
    private CustomerWriteConverter writeConverter;

    @BeforeEach
    void setUp() {
        readConverter = new CustomerReadConverter();
        writeConverter = new CustomerWriteConverter();
    }

    @Test
    void toDto_null() {
        final CustomerDto customerDto = readConverter.toDto(null);
        assertNull(customerDto);
    }

    @Test
    void toEntity_null() {
        final CustomerEntity customerEntity = writeConverter.toEntity(null);
        assertNull(customerEntity);
    }

}