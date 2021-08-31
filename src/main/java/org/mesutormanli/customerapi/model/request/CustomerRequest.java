package org.mesutormanli.customerapi.model.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CustomerRequest implements Serializable {
    private String name;
    private String surname;
    private Integer age;
    private String address;
    private String telephone;
    private String email;
    private String nationality;
    private String maritalStatus;
}
