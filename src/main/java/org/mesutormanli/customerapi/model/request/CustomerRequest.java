package org.mesutormanli.customerapi.model.request;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record CustomerRequest(
        String name,
        String surname,
        Integer age,
        String address,
        String telephone,
        String email,
        String nationality,
        String maritalStatus) implements Serializable {
}
