package com.yil.person.controller;

import com.yil.person.base.ApiHeaders;
import com.yil.person.base.PageDto;
import com.yil.person.dto.CreatePersonPhoneDto;
import com.yil.person.dto.PersonPhoneDto;
import com.yil.person.model.PersonPhone;
import com.yil.person.service.PersonPhoneService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "v1/persons/{personId}/phones")
public class PersonPhoneController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final PersonPhoneService personPhoneService;

    @Autowired
    public PersonPhoneController(PersonPhoneService personPhoneService) {
        this.personPhoneService = personPhoneService;
    }

    @GetMapping
    public ResponseEntity<PageDto<PersonPhoneDto>> findAll(
            @PathVariable Long personId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "1000") int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<PersonPhone> personPage = personPhoneService.findAllByAndPersonIdAndDeletedTimeIsNull(pageable, personId);
            PageDto<PersonPhoneDto> pageDto = PageDto.toDto(personPage, PersonPhoneService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonPhoneDto> findById(
            @PathVariable Long personId,
            @PathVariable Long id) {
        try {
            PersonPhone person;
            try {
                person = personPhoneService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!person.getPersonId().equals(personId))
                return ResponseEntity.notFound().build();
            PersonPhoneDto dto = PersonPhoneService.toDto(person);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPersonPhoneId,
                                 @PathVariable Long personId,
                                 @Valid @RequestBody CreatePersonPhoneDto dto) {
        try {
            PersonPhone entity = new PersonPhone();
            entity.setPersonId(personId);
            entity.setNumber(dto.getNumber());
            entity.setPhoneTypeId(dto.getPhoneTypeId());
            entity.setCreatedUserId(authenticatedPersonPhoneId);
            entity.setCreatedTime(new Date());
            entity = personPhoneService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPersonPhoneId,
                                  @PathVariable Long personId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreatePersonPhoneDto dto) {
        try {
            PersonPhone entity = null;
            try {
                entity = personPhoneService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getPersonId().equals(personId))
                return ResponseEntity.notFound().build();
            entity.setPhoneTypeId(dto.getPhoneTypeId());
            entity.setNumber(dto.getNumber());
            entity = personPhoneService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPersonPhoneId,
                                         @PathVariable Long personId,
                                         @PathVariable Long id) {
        try {
            PersonPhone entity;
            try {
                entity = personPhoneService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getPersonId().equals(personId))
                return ResponseEntity.notFound().build();
            entity.setDeletedUserId(authenticatedPersonPhoneId);
            entity.setDeletedTime(new Date());
            entity = personPhoneService.save(entity);
            return ResponseEntity.ok("Person phone deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
