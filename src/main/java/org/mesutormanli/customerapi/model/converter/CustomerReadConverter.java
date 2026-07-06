package org.mesutormanli.customerapi.model.converter;

import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public final class CustomerReadConverter {

    public CustomerDto toDto(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        return CustomerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .age(entity.getAge())
                .address(entity.getAddress())
                .telephone(entity.getTelephone())
                .email(entity.getEmail())
                .nationality(entity.getNationality())
                .maritalStatus(entity.getMaritalStatus())
                .build();
    }

}
