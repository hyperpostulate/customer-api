package org.mesutormanli.customerapi.model.converter;

import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.entity.CustomerEntity;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.springframework.stereotype.Component;

@Component
public final class CustomerConverter {
    public CustomerDto toDto(CustomerEntity entity) {
        if (entity == null) {
            return null;
        } else {
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

    public CustomerEntity toEntity(CustomerRequest request) {
        if (request == null) {
            return null;
        } else {
            return CustomerEntity.builder()
                    .name(request.getName())
                    .surname(request.getSurname())
                    .age(request.getAge())
                    .address(request.getAddress())
                    .telephone(request.getTelephone())
                    .email(request.getEmail())
                    .nationality(request.getNationality())
                    .maritalStatus(request.getMaritalStatus())
                    .build();
        }
    }

}
