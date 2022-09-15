package com.yil.person.service;

import com.yil.person.dto.PersonEducationDto;
import com.yil.person.dto.PersonEducationRequest;
import com.yil.person.exception.PersonEducationNotFoundException;
import com.yil.person.exception.PersonNotFoundException;
import com.yil.person.model.PersonEducation;
import com.yil.person.repository.PersonEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonEducationService {
    private final PersonEducationRepository personEducationRepository;
    private final PersonService personService;

    public static PersonEducationDto toDto(PersonEducation personEducation) throws NullPointerException {
        return PersonEducationDto.builder()
                .id(personEducation.getId())
                .educationId(personEducation.getEducationId())
                .finishDate(personEducation.getFinishDate())
                .startDate(personEducation.getStartDate())
                .build();
    }

    public PersonEducation findById(Long id) throws PersonEducationNotFoundException {
        return personEducationRepository.findById(id).orElseThrow(PersonEducationNotFoundException::new);


    }

    private PersonEducation getPersonEducation(PersonEducationRequest dto, Long personId, long userId, PersonEducation personEducation) throws PersonNotFoundException {
        if (!personService.existsById(personId))
            throw new PersonNotFoundException();
        personEducation.setEducationId(dto.getEducationId());
        personEducation.setPersonId(personId);
        personEducation.setStartDate(dto.getStartDate());
        personEducation.setFinishDate(dto.getFinishDate());
        return personEducationRepository.save(personEducation);
    }


    public PersonEducation save(PersonEducationRequest dto, Long personId, long userId) throws PersonNotFoundException {
        PersonEducation personEducation = new PersonEducation();
        return getPersonEducation(dto, personId, userId, personEducation);
    }

    public Page<PersonEducation> findAllByPersonId(Pageable pageable, long personId) {
        return personEducationRepository.findAllByPersonId(pageable, personId);
    }

    public void delete(PersonEducation personEducation) {
        personEducationRepository.delete(personEducation);
    }

    public PersonEducation replace(Long id, Long personId, PersonEducationRequest dto, Long authenticatedUserId) throws PersonEducationNotFoundException, PersonNotFoundException {
        PersonEducation personEducation = findById(id);
        return getPersonEducation(dto, personId, authenticatedUserId, personEducation);
    }
}
