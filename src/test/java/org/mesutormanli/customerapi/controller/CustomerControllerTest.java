package org.mesutormanli.customerapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mesutormanli.customerapi.base.BaseControllerTest;
import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.customerapi.model.response.CustomerListResponse;
import org.mesutormanli.customerapi.service.CustomerService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mesutormanli.customerapi.builder.CustomerMockDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CustomerController.class)
class CustomerControllerTest extends BaseControllerTest {

    private static final long CUSTOMER_ID = 1;
    private ResponseEntity<CustomerListResponse> customerListResponse;
    private CustomerRequest customerRequest;
    private ResponseEntity<CustomerDto> customerResponse;
    private ResponseEntity<CustomerDeleteResponse> customerDeleteResponse;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerListResponse = generateCustomerListResponse(CUSTOMER_ID);
        customerRequest = generateCustomerRequest();
        customerResponse = generateCustomerResponse(CUSTOMER_ID);
        customerDeleteResponse = generateCustomerDeleteResponse();
    }


    @Test
    void getCustomer() {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(customerListResponse);
        try {
            mockMvc.perform(get("/customer/{id}", CUSTOMER_ID))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).getCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(customerService);
    }


    @Test
    void getAllCustomers() {
        when(customerService.getAllCustomers()).thenReturn(customerListResponse);
        try {
            mockMvc.perform(get("/customers"))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).getAllCustomers();
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void createCustomer() {
        when(customerService.createCustomer(customerRequest)).thenReturn(customerResponse);

        try {
            mockMvc.perform(post("/customer")
                    .contentType(contentType)
                    .content(json(customerRequest))
            )
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).createCustomer(customerRequest);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void updateCustomer() {
        when(customerService.updateCustomer(CUSTOMER_ID, customerRequest)).thenReturn(customerResponse);
        try {
            mockMvc.perform(put("/customer/{id}", CUSTOMER_ID)
                    .contentType(contentType)
                    .content(json(customerRequest))
            )
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).updateCustomer(CUSTOMER_ID, customerRequest);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void deleteCustomer() {
        when(customerService.deleteCustomer(CUSTOMER_ID)).thenReturn(customerDeleteResponse);
        try {
            mockMvc.perform(delete("/customer/{id}", CUSTOMER_ID))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).deleteCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void deleteAllCustomers() {
        when(customerService.deleteAllCustomers()).thenReturn(customerDeleteResponse);
        try {
            mockMvc.perform(delete("/customers"))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).deleteAllCustomers();
        verifyNoMoreInteractions(customerService);
    }
}




