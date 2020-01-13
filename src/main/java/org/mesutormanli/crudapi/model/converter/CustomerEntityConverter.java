package org.mesutormanli.crudapi.model.converter;

import org.mesutormanli.crudapi.model.dto.CustomerDto;
import org.mesutormanli.crudapi.model.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityConverter {
    public CustomerDto toDto(CustomerEntity entity) {
        if (entity == null) {
            return null;
        } else {
            return new CustomerDto()
                    .name(entity.getName())
                    .age(entity.getAge());
        }
    }

    public CustomerEntity toEntity(CustomerDto dto) {
        if (dto == null) {
            return null;
        } else {
            return new CustomerEntity()
                    .name(dto.getName())
                    .age(dto.getAge());
        }
    }
}
