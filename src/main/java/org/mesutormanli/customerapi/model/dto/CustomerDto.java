package org.mesutormanli.customerapi.model.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record CustomerDto(
        Long id,
        String name,
        String surname,
        Integer age,
        String address,
        String telephone,
        String email,
        String nationality,
        String maritalStatus) implements Serializable {
}
