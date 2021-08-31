package org.mesutormanli.customerapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String address;
    private String telephone;
    private String email;
    private String nationality;
    private String maritalStatus;
}
