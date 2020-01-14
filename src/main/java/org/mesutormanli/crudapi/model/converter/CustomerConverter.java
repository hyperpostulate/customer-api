package org.mesutormanli.crudapi.model.converter;

import org.mesutormanli.crudapi.model.dto.CustomerDto;
import org.mesutormanli.crudapi.model.entity.CustomerEntity;
import org.mesutormanli.crudapi.model.request.CustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {
    public CustomerDto toDto(CustomerEntity entity) {
        if (entity == null) {
            return null;
        } else {
            return new CustomerDto()
                    .id(entity.getId())
                    .name(entity.getName())
                    .age(entity.getAge());
        }
    }

    public CustomerEntity toEntity(CustomerRequest request) {
        if (request == null) {
            return null;
        } else {
            return new CustomerEntity()
                    .name(request.getName())
                    .age(request.getAge());
        }
    }

}
