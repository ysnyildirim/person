package com.yil.person.service;

import com.yil.person.dto.PersonDto;
import com.yil.person.model.Person;
import com.yil.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findById(Long id) throws EntityNotFoundException {
        return personRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException();
        });
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Page<Person> findAllByDeletedTimeIsNull(Pageable pageable) {
        return personRepository.findAllByDeletedTimeIsNull(pageable);
    }

    public static PersonDto toDto(Person person) throws NullPointerException {
        if (person == null)
            throw new NullPointerException("Person is null");
        PersonDto dto = new PersonDto();
        dto.setId(person.getId());
        dto.setUserId(person.getUserId());
        dto.setBirthDate(person.getBirthDate());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setIdentificationNumber(person.getIdentificationNumber());
        return dto;
    }
}
