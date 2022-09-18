package com.yil.person.service;

import com.yil.person.dto.CreatePersonDto;
import com.yil.person.dto.PersonDto;
import com.yil.person.exception.EducationNotFoundException;
import com.yil.person.exception.GenderNotFoundException;
import com.yil.person.exception.PersonNotFoundException;
import com.yil.person.model.Person;
import com.yil.person.repository.GenderTypeRepository;
import com.yil.person.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final GenderTypeRepository genderDao;

    public static PersonDto toDto(Person person) throws NullPointerException {
        if (person == null)
            throw new NullPointerException("Person is null");
        PersonDto dto = new PersonDto();
        dto.setId(person.getId());
        dto.setBirthDate(person.getBirthDate());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setIdentificationNumber(person.getIdentificationNumber());
        dto.setJobId(person.getJobId());
        return dto;
    }

    public boolean existsById(long id) {
        return personRepository.existsById(id);
    }

    public Person save(CreatePersonDto dto, long userId) throws GenderNotFoundException, EducationNotFoundException {
        Person person = new Person();
        return getPerson(dto, userId, person);
    }

    private Person getPerson(CreatePersonDto dto, long userId, Person person) throws GenderNotFoundException {
        if (!genderDao.existsById(dto.getGenderId()))
            throw new GenderNotFoundException();
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setBirthDate(dto.getBirthDate());
        person.setIdentificationNumber(dto.getIdentificationNumber());
        person.setContactId(dto.getContactId());
        person.setCreatedUserId(userId);
        person.setCreatedTime(new Date());
        person.setGenderId(dto.getGenderId());
        person.setJobId(dto.getJobId());
        return personRepository.save(person);
    }

    public Person replace(long id, CreatePersonDto dto, long userId) throws PersonNotFoundException, GenderNotFoundException {
        Person person = findById(id);
        return getPerson(dto, userId, person);
    }

    public Person findById(Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

    }

    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }

    public void deleteById(long id) {
        personRepository.deleteById(id);
    }
}
