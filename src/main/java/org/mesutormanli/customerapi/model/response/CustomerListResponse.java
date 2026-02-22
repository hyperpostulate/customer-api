package org.mesutormanli.customerapi.model.response;

import lombok.Builder;
import org.mesutormanli.customerapi.model.dto.CustomerDto;

import java.io.Serializable;
import java.util.List;

@Builder
public record CustomerListResponse(
        List<CustomerDto> customers) implements Serializable {
}
