package com.yil.person.service;

import com.yil.person.dto.PersonEmailDto;
import com.yil.person.model.PersonEmail;
import com.yil.person.repository.PersonEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PersonEmailService {
    private final PersonEmailRepository personEmailRepository;

    @Autowired
    public PersonEmailService(PersonEmailRepository personEmailRepository) {
        this.personEmailRepository = personEmailRepository;
    }

    public PersonEmail findById(Long id) throws EntityNotFoundException {
        return personEmailRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public PersonEmail save(PersonEmail person) {
        return personEmailRepository.save(person);
    }


    public static PersonEmailDto toDto(PersonEmail personEmail) {
        if (personEmail == null)
            throw new NullPointerException("Person email is null");
        PersonEmailDto dto = new PersonEmailDto();
        dto.setPersonId(personEmail.getPersonId());
        dto.setId(personEmail.getId());
        dto.setEmailTypeId(personEmail.getEmailTypeId());
        dto.setAddress(personEmail.getAddress());
        return dto;
    }

    public Page<PersonEmail> findAllByPersonIdAndDeletedTimeIsNull(Pageable pageable, Long personId) {
        return personEmailRepository.findAllByPersonIdAndDeletedTimeIsNull(pageable,personId);
    }
}
