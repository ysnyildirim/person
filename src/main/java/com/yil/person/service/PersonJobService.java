package com.yil.person.service;

import com.yil.person.dto.PersonJobDto;
import com.yil.person.dto.PersonJobRequest;
import com.yil.person.exception.PersonNotFoundException;
import com.yil.person.model.PersonJob;
import com.yil.person.repository.PersonJobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonJobService {
    private final PersonJobRepository personJobRepository;
    private final PersonService personService;

    public static PersonJobDto toDto(PersonJob personJob) {
        return PersonJobDto.builder()
                .jobId(personJob.getId().getJobId())
                .personId(personJob.getId().getPersonId())
                .build();
    }

    public PersonJob save(PersonJobRequest dto, Long personId, long userId) throws PersonNotFoundException {
        if (!personService.existsById(personId))
            throw new PersonNotFoundException();
        PersonJob.Pk pk = new PersonJob.Pk();
        pk.setPersonId(personId);
        pk.setJobId(dto.getJobId());
        PersonJob personJob = new PersonJob();
        personJob.setId(pk);
        return personJobRepository.save(personJob);
    }

    public List<PersonJob> findAllByPersonId(Long personId) {
        return personJobRepository.findAllByPersonId(personId);
    }
}
