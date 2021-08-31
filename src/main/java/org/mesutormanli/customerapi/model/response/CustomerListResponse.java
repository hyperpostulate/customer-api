package org.mesutormanli.customerapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mesutormanli.customerapi.model.dto.CustomerDto;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListResponse implements Serializable {
    private List<CustomerDto> customers;
}
