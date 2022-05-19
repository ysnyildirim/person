package com.yil.person.service;

import com.yil.person.dto.PersonAddressDto;
import com.yil.person.model.PersonAddress;
import com.yil.person.repository.PersonAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PersonAddressService {
    private final PersonAddressRepository personAddressRepository;

    @Autowired
    public PersonAddressService(PersonAddressRepository personAddressRepository) {
        this.personAddressRepository = personAddressRepository;
    }

    public PersonAddress findById(Long id) throws EntityNotFoundException {
        return personAddressRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public PersonAddress save(PersonAddress person) {
        return personAddressRepository.save(person);
    }

    public Page<PersonAddress> findAllByAndPersonIdAndDeletedTimeIsNull(Pageable pageable,Long personId) {
        return personAddressRepository.findAllByAndPersonIdAndDeletedTimeIsNull(pageable,personId);
    }

    public static PersonAddressDto toDto(PersonAddress entity) {
        if (entity == null)
            throw new NullPointerException("Person address is null");
        PersonAddressDto dto = new PersonAddressDto();
        dto.setId(entity.getId());
        dto.setPersonId(dto.getPersonId());
        dto.setCountryId(dto.getCountryId());
        dto.setCityId(dto.getCityId());
        dto.setDistrictId(dto.getDistrictId());
        dto.setStreetId(dto.getStreetId());
        dto.setExteriorDoorId(dto.getExteriorDoorId());
        dto.setInteriorDoorId(dto.getInteriorDoorId());
        return dto;
    }
}
