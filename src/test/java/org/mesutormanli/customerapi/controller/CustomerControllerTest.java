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

import static org.junit.jupiter.api.Assertions.fail;
import static org.mesutormanli.customerapi.builder.CustomerMockDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(value = CustomerController.class)
class CustomerControllerTest extends BaseControllerTest {

    private static final long CUSTOMER_ID = 1;
    private CustomerListResponse customerListResponse;
    private CustomerRequest customerRequest;
    private CustomerDto customerDto;
    private CustomerDeleteResponse customerDeleteResponse;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerListResponse = generateCustomerListResponse(CUSTOMER_ID);
        customerRequest = generateCustomerRequest();
        customerDto = generateCustomerDto(CUSTOMER_ID);
        customerDeleteResponse = generateCustomerDeleteResponse();
        this.mockMvc = webAppContextSetup(wac)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    //TODO: write authorization tests for non-get requests
    @Test
    void getCustomer() {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(customerListResponse);
        try {
            mockMvc.perform(get("/customer/{id}", CUSTOMER_ID)
                            .with(httpBasic("user", "password"))
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).getCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void getCustomer_notFound() {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(new CustomerListResponse());
        try {
            mockMvc.perform(get("/customer/{id}", CUSTOMER_ID)
                            .with(httpBasic("user", "password"))
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isNotFound());
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
            mockMvc.perform(get("/customers")
                            .with(httpBasic("user", "password"))
                            .with(csrf()))
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
        when(customerService.createCustomer(customerRequest)).thenReturn(customerDto);

        try {
            mockMvc.perform(post("/customer").with(httpBasic("admin", "admin"))
                            .with(csrf())
                            .contentType(contentType)
                            .content(json(customerRequest))
                    )
                    .andDo(print())
                    .andExpect(status().isCreated());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).createCustomer(customerRequest);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void updateCustomer() {
        when(customerService.updateCustomer(CUSTOMER_ID, customerRequest)).thenReturn(customerDto);
        try {
            mockMvc.perform(put("/customer/{id}", CUSTOMER_ID)
                            .with(httpBasic("admin", "admin"))
                            .with(csrf())
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
    void updateCustomer_notFound() {
        when(customerService.updateCustomer(CUSTOMER_ID, customerRequest)).thenReturn(null);
        try {
            mockMvc.perform(put("/customer/{id}", CUSTOMER_ID)
                            .with(httpBasic("admin", "admin"))
                            .with(csrf())
                            .contentType(contentType)
                            .content(json(customerRequest))
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound());
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
            mockMvc.perform(delete("/customer/{id}", CUSTOMER_ID)
                            .with(httpBasic("admin", "admin"))
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).deleteCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void deleteCustomer_noContent() {
        when(customerService.deleteCustomer(CUSTOMER_ID)).thenReturn(CustomerDeleteResponse.builder().deletedCustomerCount(0L).build());
        try {
            mockMvc.perform(delete("/customer/{id}", CUSTOMER_ID)
                            .with(httpBasic("admin", "admin"))
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isNoContent());
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
            mockMvc.perform(delete("/customers")
                            .with(httpBasic("admin", "admin"))
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).deleteAllCustomers();
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void deleteAllCustomers_noContent() {
        when(customerService.deleteAllCustomers()).thenReturn(CustomerDeleteResponse.builder().deletedCustomerCount(0L).build());
        try {
            mockMvc.perform(delete("/customers")
                            .with(httpBasic("admin", "admin"))
                            .with(csrf()))
                    .andDo(print())
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).deleteAllCustomers();
        verifyNoMoreInteractions(customerService);
    }
}




