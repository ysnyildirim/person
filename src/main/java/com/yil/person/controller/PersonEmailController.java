package com.yil.person.controller;

import com.yil.person.base.ApiHeaders;
import com.yil.person.base.PageDto;
import com.yil.person.dto.CreatePersonEmailDto;
import com.yil.person.dto.PersonEmailDto;
import com.yil.person.model.PersonEmail;
import com.yil.person.service.PersonEmailService;
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
@RequestMapping(value = "v1/persons/{personId}/emails")
public class PersonEmailController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final PersonEmailService personEmailService;

    @Autowired
    public PersonEmailController(PersonEmailService personEmailService) {
        this.personEmailService = personEmailService;
    }

    @GetMapping
    public ResponseEntity<PageDto<PersonEmailDto>> findAll(
            @PathVariable Long personId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "1000") int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<PersonEmail> personPage = personEmailService.findAllByPersonIdAndDeletedTimeIsNull(pageable,personId);
            PageDto<PersonEmailDto> pageDto = PageDto.toDto(personPage, PersonEmailService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonEmailDto> findById(
            @PathVariable Long personId,
            @PathVariable Long id) {
        try {
            PersonEmail person;
            try {
                person = personEmailService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!person.getPersonId().equals(personId))
                return ResponseEntity.notFound().build();
            PersonEmailDto dto = PersonEmailService.toDto(person);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPersonEmailId,
                                 @PathVariable Long personId,
                                 @Valid @RequestBody CreatePersonEmailDto dto) {
        try {
            PersonEmail entity = new PersonEmail();
            entity.setPersonId(personId);
            entity.setAddress(dto.getAddress());
            entity.setEmailTypeId(dto.getEmailTypeId());
            entity.setCreatedUserId(authenticatedPersonEmailId);
            entity.setCreatedTime(new Date());
            entity = personEmailService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPersonEmailId,
                                  @PathVariable Long personId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreatePersonEmailDto dto) {
        try {
            PersonEmail entity = null;
            try {
                entity = personEmailService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getPersonId().equals(personId))
                return ResponseEntity.notFound().build();
            entity.setEmailTypeId(dto.getEmailTypeId());
            entity.setAddress(dto.getAddress());
            entity = personEmailService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPersonEmailId,
                                         @PathVariable Long personId,
                                         @PathVariable Long id) {
        try {
            PersonEmail entity;
            try {
                entity = personEmailService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getPersonId().equals(personId))
                return ResponseEntity.notFound().build();
            entity.setDeletedUserId(authenticatedPersonEmailId);
            entity.setDeletedTime(new Date());
            entity = personEmailService.save(entity);
            return ResponseEntity.ok("Person email deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
