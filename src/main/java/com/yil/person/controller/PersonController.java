package com.yil.person.controller;

import com.yil.person.base.ApiConstant;
import com.yil.person.base.PageDto;
import com.yil.person.dto.CreatePersonDto;
import com.yil.person.dto.PersonDto;
import com.yil.person.exception.EducationNotFoundException;
import com.yil.person.exception.GenderNotFoundException;
import com.yil.person.exception.PersonNotFoundException;
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

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/prs/v1/persons")
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
    public ResponseEntity<String> create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @Valid @RequestBody CreatePersonDto dto) throws GenderNotFoundException, EducationNotFoundException {
        personService.save(dto, authenticatedUserId);
        return ResponseEntity.created(null).body("Kişi eklendi");
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreatePersonDto dto) throws PersonNotFoundException, GenderNotFoundException {
        personService.replace(id, dto, authenticatedUserId);
        return ResponseEntity.ok().build();

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
