package com.yil.person.controller;

import com.yil.person.base.ApiConstant;
import com.yil.person.base.PageDto;
import com.yil.person.dto.PersonEducationDto;
import com.yil.person.dto.PersonEducationRequest;
import com.yil.person.exception.PersonEducationNotFoundException;
import com.yil.person.exception.PersonNotFoundException;
import com.yil.person.model.PersonEducation;
import com.yil.person.service.PersonEducationService;
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
@RequestMapping(value = "/api/prs/v1/persons/{personId}/educations")
public class PersonEducationController {

    private final Log logger = LogFactory.getLog(this.getClass());
    private final PersonEducationService personEducationService;

    @GetMapping
    public ResponseEntity<PageDto<PersonEducationDto>> findAll(
            @RequestHeader(value = "personId") Long personId,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        try {
            if (page < 0)
                page = 0;
            if (size <= 0 || size > 1000)
                size = 1000;
            Pageable pageable = PageRequest.of(page, size);
            Page<PersonEducation> personPage = personEducationService.findAllByPersonId(pageable, personId);
            PageDto<PersonEducationDto> pageDto = PageDto.toDto(personPage, PersonEducationService::toDto);
            return ResponseEntity.ok(pageDto);
        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonEducationDto> findById(
            @RequestHeader(value = "personId") Long personId,
            @PathVariable Long id) throws PersonEducationNotFoundException {
        PersonEducation personEducation = personEducationService.findById(id);
        PersonEducationDto dto = PersonEducationService.toDto(personEducation);
        return ResponseEntity.ok(dto);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(
            @RequestHeader(value = "personId") Long personId,
            @RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
            @Valid @RequestBody PersonEducationRequest dto) throws PersonNotFoundException {
        personEducationService.save(dto, personId, authenticatedUserId);
        return ResponseEntity.created(null).body("Kişiye ait eğitim bilgisi eklendi");
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity replace(
            @RequestHeader(value = "personId") Long personId,
            @RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
            @PathVariable Long id,
            @Valid @RequestBody PersonEducationRequest dto) throws PersonEducationNotFoundException, PersonNotFoundException {
        personEducationService.replace(id, personId, dto, authenticatedUserId);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> delete(
            @RequestHeader(value = "personId") Long personId,
            @RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedPersonId,
            @PathVariable Long id) {

        try {
            PersonEducation personEducation;
            try {
                personEducation = personEducationService.findById(id);
            } catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                throw e;
            }
            personEducationService.delete(personEducation);
            return ResponseEntity.ok("Person deleted.");

        } catch (Exception exception) {
            logger.error(null, exception);
            return ResponseEntity.internalServerError().build();
        }
    }


}
