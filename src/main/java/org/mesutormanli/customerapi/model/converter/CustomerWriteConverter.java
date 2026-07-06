package org.mesutormanli.customerapi.model.converter;

import org.mesutormanli.customerapi.model.entity.CustomerEntity;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.springframework.stereotype.Component;

@Component
public final class CustomerWriteConverter {

    public CustomerEntity toEntity(CustomerRequest request) {
        if (request == null) {
            return null;
        }
        return CustomerEntity.builder()
                .name(request.name())
                .surname(request.surname())
                .age(request.age())
                .address(request.address())
                .telephone(request.telephone())
                .email(request.email())
                .nationality(request.nationality())
                .maritalStatus(request.maritalStatus())
                .build();
    }

}
