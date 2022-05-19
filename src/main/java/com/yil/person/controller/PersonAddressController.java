package com.yil.person.controller;

import com.yil.person.base.ApiHeaders;
import com.yil.person.base.PageDto;
import com.yil.person.dto.CreatePersonAddressDto;
import com.yil.person.dto.PersonAddressDto;
import com.yil.person.model.PersonAddress;
import com.yil.person.service.PersonAddressService;
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
@RequestMapping(value = "v1/persons/{personId}/address")
public class PersonAddressController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final PersonAddressService personAddressService;

    @Autowired
    public PersonAddressController(PersonAddressService personAddressService) {
        this.personAddressService = personAddressService;
    }

    @GetMapping
    public ResponseEntity<PageDto<PersonAddressDto>> findAll(
            @PathVariable Long personId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "1000") int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<PersonAddress> personPage = personAddressService.findAllByAndPersonIdAndDeletedTimeIsNull(pageable, personId);
            PageDto<PersonAddressDto> pageDto = PageDto.toDto(personPage, PersonAddressService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonAddressDto> findById(
            @PathVariable Long personId,
            @PathVariable Long id) {
        try {
            PersonAddress entity;
            try {
                entity = personAddressService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getPersonId().equals(personId))
                return ResponseEntity.notFound().build();
            PersonAddressDto dto = PersonAddressService.toDto(entity);
            return ResponseEntity.ok(dto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPersonAddressId,
                                 @PathVariable Long personId,
                                 @Valid @RequestBody CreatePersonAddressDto dto) {
        try {
            PersonAddress entity = new PersonAddress();
            entity.setPersonId(personId);
            entity.setCountryId(dto.getCountryId());
            entity.setCityId(dto.getCityId());
            entity.setDistrictId(dto.getDistrictId());
            entity.setStreetId(dto.getStreetId());
            entity.setExteriorDoorId(dto.getExteriorDoorId());
            entity.setInteriorDoorId(dto.getInteriorDoorId());
            entity.setCreatedUserId(authenticatedPersonAddressId);
            entity.setCreatedTime(new Date());
            entity = personAddressService.save(entity);
            return ResponseEntity.created(null).build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPersonAddressId,
                                  @PathVariable Long personId,
                                  @PathVariable Long id,
                                  @Valid @RequestBody CreatePersonAddressDto dto) {
        try {
            PersonAddress entity = null;
            try {
                entity = personAddressService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
            if (!entity.getPersonId().equals(personId))
                return ResponseEntity.notFound().build();
            entity.setCountryId(dto.getCountryId());
            entity.setCityId(dto.getCityId());
            entity.setDistrictId(dto.getDistrictId());
            entity.setStreetId(dto.getStreetId());
            entity.setExteriorDoorId(dto.getExteriorDoorId());
            entity.setInteriorDoorId(dto.getInteriorDoorId());
            entity = personAddressService.save(entity);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(@RequestHeader(value = ApiHeaders.AUTHENTICATED_USER_ID) Long authenticatedPersonAddressId,
                                         @PathVariable Long personId,
                                         @PathVariable Long id) {
        try {
            PersonAddress entity;
            try {
                entity = personAddressService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            if (!entity.getPersonId().equals(personId))
                return ResponseEntity.notFound().build();
            entity.setDeletedUserId(authenticatedPersonAddressId);
            entity.setDeletedTime(new Date());
            entity = personAddressService.save(entity);
            return ResponseEntity.ok("Person address deleted.");
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
