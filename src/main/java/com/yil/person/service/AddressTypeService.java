package com.yil.person.service;

import com.yil.person.dto.AddressTypeDto;
import com.yil.person.model.AddressType;
import com.yil.person.repository.AddressTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AddressTypeService {

    private final AddressTypeRepository addressTypeRepository;

    @Autowired
    public AddressTypeService(AddressTypeRepository addressTypeRepository) {
        this.addressTypeRepository = addressTypeRepository;
    }

    public static AddressTypeDto toDto(AddressType f) {
        if (f == null)
            throw new NullPointerException("Address type is null");
        AddressTypeDto dto = new AddressTypeDto();
        dto.setId(f.getId());
        dto.setName(f.getName());
        return dto;
    }

    public AddressType save(AddressType addressType) {
        return addressTypeRepository.save(addressType);
    }

    public AddressType findById(Long id) throws EntityNotFoundException {
        return addressTypeRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException();
        });
    }

    public List<AddressType> findAllByDeletedTimeIsNull() {
        return addressTypeRepository.findAllByDeletedTimeIsNull();
    }

}
