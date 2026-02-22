package org.mesutormanli.customerapi.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerEntityTest {

    @Test
    void testGettersAndSetters() {
        CustomerEntity entity = new CustomerEntity();

        entity.setId(1L);
        entity.setName("John");
        entity.setSurname("Doe");
        entity.setAge(30);
        entity.setAddress("123 Main St");
        entity.setTelephone("555-1234");
        entity.setEmail("john@example.com");
        entity.setNationality("US");
        entity.setMaritalStatus("Single");
        entity.setDeleted(true);

        assertEquals(1L, entity.getId());
        assertEquals("John", entity.getName());
        assertEquals("Doe", entity.getSurname());
        assertEquals(30, entity.getAge());
        assertEquals("123 Main St", entity.getAddress());
        assertEquals("555-1234", entity.getTelephone());
        assertEquals("john@example.com", entity.getEmail());
        assertEquals("US", entity.getNationality());
        assertEquals("Single", entity.getMaritalStatus());
        assertTrue(entity.isDeleted());
    }

    @Test
    void testBuilder() {
        CustomerEntity entity = CustomerEntity.builder()
                .id(2L)
                .name("Jane")
                .surname("Doe")
                .age(25)
                .address("456 Elm St")
                .telephone("555-5678")
                .email("jane@example.com")
                .nationality("UK")
                .maritalStatus("Married")
                .deleted(false)
                .build();

        assertEquals(2L, entity.getId());
        assertEquals("Jane", entity.getName());
        assertEquals("Doe", entity.getSurname());
        assertEquals(25, entity.getAge());
        assertEquals("456 Elm St", entity.getAddress());
        assertEquals("555-5678", entity.getTelephone());
        assertEquals("jane@example.com", entity.getEmail());
        assertEquals("UK", entity.getNationality());
        assertEquals("Married", entity.getMaritalStatus());
        assertFalse(entity.isDeleted());
    }

    @Test
    void testEqualsAndHashCode() {
        CustomerEntity entity1 = CustomerEntity.builder().id(1L).name("John").surname("Doe").build();
        CustomerEntity entity2 = CustomerEntity.builder().id(1L).name("John").surname("Doe").build();
        CustomerEntity entity3 = CustomerEntity.builder().id(2L).name("Jane").surname("Doe").build();
        CustomerEntity entity4 = new CustomerEntity();
        CustomerEntity entity5 = new CustomerEntity();

        assertEquals(entity1, entity1);
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
        assertNotEquals(entity1, null);
        assertNotEquals(entity1, new Object());
        assertEquals(entity4, entity5);

        assertEquals(entity1.hashCode(), entity2.hashCode());
        assertNotEquals(entity1.hashCode(), entity3.hashCode());
    }

    @Test
    void testToString() {
        CustomerEntity entity = CustomerEntity.builder().id(1L).name("John").surname("Doe").build();
        String toString = entity.toString();

        assertTrue(toString.contains("CustomerEntity{"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name='John'"));
        assertTrue(toString.contains("surname='Doe'"));
    }
}
