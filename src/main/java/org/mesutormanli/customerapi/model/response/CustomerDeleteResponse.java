package org.mesutormanli.customerapi.model.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public final class CustomerDeleteResponse implements Serializable {
    private Long deletedCustomerCount;
}
