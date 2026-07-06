package org.mesutormanli.customerapi.service.impl;

import org.mesutormanli.customerapi.model.converter.CustomerReadConverter;
import org.mesutormanli.customerapi.model.converter.CustomerWriteConverter;
import org.mesutormanli.customerapi.model.dto.CustomerDto;
import org.mesutormanli.customerapi.model.entity.CustomerEntity;
import org.mesutormanli.customerapi.model.request.CustomerRequest;
import org.mesutormanli.customerapi.repository.CustomerRepository;
import org.mesutormanli.customerapi.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerReadConverter readConverter;
    private final CustomerWriteConverter writeConverter;

    public CustomerServiceImpl(CustomerRepository repository, CustomerReadConverter readConverter,
            CustomerWriteConverter writeConverter) {
        this.repository = repository;
        this.readConverter = readConverter;
        this.writeConverter = writeConverter;
    }

    @Override
    public Optional<CustomerDto> getCustomer(Long id) {
        return repository.findById(id).map(readConverter::toDto);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return repository.findAll()
                .stream()
                .map(readConverter::toDto)
                .toList();
    }

    @Override
    public CustomerDto createCustomer(CustomerRequest request) {
        final CustomerEntity saved = repository.save(writeConverter.toEntity(request));
        return readConverter.toDto(saved);
    }

    @Override
    public Optional<CustomerDto> updateCustomer(Long id, CustomerRequest request) {
        return repository.findById(id)
                .map(existing -> {
                    final CustomerEntity toBeUpdated = writeConverter.toEntity(request);
                    toBeUpdated.setId(existing.getId());
                    return repository.save(toBeUpdated);
                })
                .map(readConverter::toDto);
    }

    @Override
    public long deleteCustomer(Long id) {
        if (!repository.existsById(id)) {
            return 0L;
        }
        repository.deleteById(id);
        return 1L;
    }

    @Override
    public long deleteAllCustomers() {
        final long count = repository.count();
        if (count == 0) {
            return 0L;
        }
        repository.deleteAll();
        return count;
    }

}
