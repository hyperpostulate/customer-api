package org.mesutormanli.customerapi.model.response;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record CustomerDeleteResponse(
        Long deletedCustomerCount) implements Serializable {
}
