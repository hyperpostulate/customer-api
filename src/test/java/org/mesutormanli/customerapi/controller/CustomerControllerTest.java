package org.mesutormanli.customerapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mesutormanli.customerapi.base.BaseControllerTest;
import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.service.CustomerService;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mesutormanli.customerapi.builder.CustomerMockDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(value = CustomerController.class)
@Import(CustomerResponseMapper.class)
class CustomerControllerTest extends BaseControllerTest {

    private static final long CUSTOMER_ID = 1;
    private CustomerRequest customerRequest;
    private CustomerDto customerDto;

    @MockitoBean
    private CustomerService customerService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        customerRequest = generateCustomerRequest();
        customerDto = generateCustomerDto(CUSTOMER_ID);

        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @Test
    void getCustomer() {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customerDto));
        try {
            mockMvc.perform(get("/customer/{id}", CUSTOMER_ID))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            fail(e);
        }
        verify(customerService, times(1)).getCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void getCustomer_notFound() {
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(Optional.empty());
        try {
            mockMvc.perform(get("/customer/{id}", CUSTOMER_ID))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).getCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void getAllCustomers() {
        when(customerService.getAllCustomers()).thenReturn(Collections.singletonList(customerDto));
        try {
            mockMvc.perform(get("/customers", CUSTOMER_ID))
                    .andExpect(MockMvcResultMatchers.status().isOk());
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
            mockMvc.perform(post("/customer")
                            .contentType(contentType)
                            .content(json(customerRequest)))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).createCustomer(customerRequest);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void updateCustomer() {
        when(customerService.updateCustomer(CUSTOMER_ID, customerRequest)).thenReturn(Optional.of(customerDto));
        try {
            mockMvc.perform(put("/customer/{id}", CUSTOMER_ID)
                            .contentType(contentType)
                            .content(json(customerRequest)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).updateCustomer(CUSTOMER_ID, customerRequest);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void updateCustomer_notFound() {
        when(customerService.updateCustomer(CUSTOMER_ID, customerRequest)).thenReturn(Optional.empty());
        try {
            mockMvc.perform(put("/customer/{id}", CUSTOMER_ID)
                            .contentType(contentType)
                            .content(json(customerRequest)))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).updateCustomer(CUSTOMER_ID, customerRequest);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void deleteCustomer() {
        when(customerService.deleteCustomer(CUSTOMER_ID)).thenReturn(1L);
        try {
            mockMvc.perform(delete("/customer/{id}", CUSTOMER_ID))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).deleteCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void deleteCustomer_noContent() {
        when(customerService.deleteCustomer(CUSTOMER_ID)).thenReturn(0L);
        try {
            mockMvc.perform(delete("/customer/{id}", CUSTOMER_ID))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).deleteCustomer(CUSTOMER_ID);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void deleteAllCustomers() {
        when(customerService.deleteAllCustomers()).thenReturn(1L);
        try {
            mockMvc.perform(delete("/customers"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).deleteAllCustomers();
        verifyNoMoreInteractions(customerService);
    }

    @Test
    void deleteAllCustomers_noContent() {
        when(customerService.deleteAllCustomers()).thenReturn(0L);
        try {
            mockMvc.perform(delete("/customers"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
        } catch (Exception e) {
            fail(e);
        }

        verify(customerService, times(1)).deleteAllCustomers();
        verifyNoMoreInteractions(customerService);
    }
}
