package com.yil.person.controller;

import com.yil.person.base.ApiConstant;
import com.yil.person.base.PageDto;
import com.yil.person.dto.CreatePersonDto;
import com.yil.person.dto.PersonDto;
import com.yil.person.model.Person;
import com.yil.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/person/v1/persons")
public class PersonController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final PersonService personService;

    @GetMapping
    public ResponseEntity<PageDto<PersonDto>> findAll(
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<Person> personPage = personService.findAll(pageable);
            PageDto<PersonDto> pageDto = PageDto.toDto(personPage, PersonService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable Long id) {
        try {
            Person person;
            try {
                person = personService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            PersonDto dto = PersonService.toDto(person);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedPersonId,
                                 @Valid @RequestBody CreatePersonDto dto) {
        try {
            Person person = new Person();
            person.setFirstName(dto.getFirstName());
            person.setLastName(dto.getLastName());
            person.setBirthDate(dto.getBirthDate());
            person.setIdentificationNumber(dto.getIdentificationNumber());
            person.setContactId(dto.getContactId());
            person.setCreatedUserId(authenticatedPersonId);
            person.setCreatedTime(new Date());
            person = personService.save(person);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedPersonId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreatePersonDto dto) {
        try {

            Person person = null;
            try {
                person = personService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            person.setFirstName(dto.getFirstName());
            person.setLastName(dto.getLastName());
            person.setBirthDate(dto.getBirthDate());
            person.setContactId(dto.getContactId());
            person.setIdentificationNumber(dto.getIdentificationNumber());
            person = personService.save(person);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedPersonId,
                                         @PathVariable Long id) {
        try {
            Person person;
            try {
                person = personService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            personService.delete(person);
            return ResponseEntity.ok("Person deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
