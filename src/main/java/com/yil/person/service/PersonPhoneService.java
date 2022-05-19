package com.yil.person.service;

import com.yil.person.dto.PersonPhoneDto;
import com.yil.person.model.PersonPhone;
import com.yil.person.repository.PersonPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PersonPhoneService {
    private final PersonPhoneRepository personPhoneRepository;

    @Autowired
    public PersonPhoneService(PersonPhoneRepository personPhoneRepository) {
        this.personPhoneRepository = personPhoneRepository;
    }

    public PersonPhone findById(Long id) throws EntityNotFoundException {
        return personPhoneRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public PersonPhone save(PersonPhone person) {
        return personPhoneRepository.save(person);
    }

    public Page<PersonPhone> findAllByAndPersonIdAndDeletedTimeIsNull(Pageable pageable, Long personId) {
        return personPhoneRepository.findAllByAndPersonIdAndDeletedTimeIsNull(pageable,personId);
    }

    public static PersonPhoneDto toDto(PersonPhone personPhone) {
        if (personPhone == null)
            throw new NullPointerException("Person phone is null");
        PersonPhoneDto dto = new PersonPhoneDto();
        dto.setId(personPhone.getId());
        dto.setPersonId(personPhone.getPersonId());
        dto.setPhoneTypeId(personPhone.getPhoneTypeId());
        dto.setNumber(personPhone.getNumber());
        return dto;
    }
}
