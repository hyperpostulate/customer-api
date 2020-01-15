package org.mesutormanli.crudapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mesutormanli.crudapi.base.BaseControllerTest;
import org.mesutormanli.crudapi.builder.GenericMockDataBuilder;
import org.mesutormanli.crudapi.model.dto.CustomerDto;
import org.mesutormanli.crudapi.model.request.CustomerRequest;
import org.mesutormanli.crudapi.model.response.CustomerDeleteResponse;
import org.mesutormanli.crudapi.model.response.CustomerListResponse;
import org.mesutormanli.crudapi.service.CustomerService;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

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
        customerListResponse = generateCustomerListResponse();
        customerRequest = generateCustomerRequest();
        customerResponse = generateCustomerResponse();
        customerDeleteResponse = generateCustomerDeleteResponse();
    }

    private ResponseEntity<CustomerListResponse> generateCustomerListResponse() {
        return new ResponseEntity<>(new CustomerListResponse().customers(Collections.singletonList(generateCustomerDto())), HttpStatus.OK);
    }

    private CustomerRequest generateCustomerRequest() {
        return GenericMockDataBuilder.of(CustomerRequest.class).build();
    }

    private ResponseEntity<CustomerDto> generateCustomerResponse() {
        return new ResponseEntity<>(generateCustomerDto(), HttpStatus.OK);
    }

    private ResponseEntity<CustomerDeleteResponse> generateCustomerDeleteResponse() {
        return new ResponseEntity<>(new CustomerDeleteResponse().deletedCustomerCount((long) 1), HttpStatus.OK);
    }

    private CustomerDto generateCustomerDto() {
        return GenericMockDataBuilder.of(CustomerDto.class)
                .excludeField("id")
                .build()
                .id(CUSTOMER_ID);
    }

    @Test
    void getCustomer() throws Exception {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(customerListResponse);
        mockMvc.perform(get(CustomerEndpoint.CUSTOMER_URL_WITH_ID, CUSTOMER_ID))
                .andDo(print())
                .andExpect(status().isOk());

        verify(customerService, times(1)).getCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(customerService);
    }


    @Test
    void getAllCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(customerListResponse);
        mockMvc.perform(get(CustomerEndpoint.CUSTOMER_BASE_URL))
                .andDo(print())
                .andExpect(status().isOk());

        verify(customerService, times(1)).getAllCustomers();
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void createCustomer() throws Exception {
        when(customerService.createCustomer(customerRequest)).thenReturn(customerResponse);

        mockMvc.perform(post(CustomerEndpoint.CUSTOMER_BASE_URL)
                .contentType(contentType)
                .content(json(customerRequest))
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(customerService, times(1)).createCustomer(customerRequest);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void updateCustomer() throws Exception {
        when(customerService.updateCustomer(CUSTOMER_ID, customerRequest)).thenReturn(customerResponse);
        mockMvc.perform(put(CustomerEndpoint.CUSTOMER_URL_WITH_ID, CUSTOMER_ID)
                .contentType(contentType)
                .content(json(customerRequest))
        )
                .andDo(print())
                .andExpect(status().isOk());

        verify(customerService, times(1)).updateCustomer(CUSTOMER_ID, customerRequest);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void deleteCustomer() throws Exception {
        when(customerService.deleteCustomer(CUSTOMER_ID)).thenReturn(customerDeleteResponse);
        mockMvc.perform(delete(CustomerEndpoint.CUSTOMER_URL_WITH_ID, CUSTOMER_ID))
                .andDo(print())
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void deleteAllCustomers() throws Exception {
        when(customerService.deleteAllCustomers()).thenReturn(customerDeleteResponse);
        mockMvc.perform(delete(CustomerEndpoint.CUSTOMER_BASE_URL))
                .andDo(print())
                .andExpect(status().isOk());

        verify(customerService, times(1)).deleteAllCustomers();
        verifyNoMoreInteractions(customerService);
    }
}




