package com.yil.person.controller;

import com.yil.person.base.ApiConstant;
import com.yil.person.base.Mapper;
import com.yil.person.dto.PersonEducationDto;
import com.yil.person.dto.PersonEducationRequest;
import com.yil.person.exception.PersonEducationNotFoundException;
import com.yil.person.exception.PersonNotFoundException;
import com.yil.person.model.PersonEducation;
import com.yil.person.service.PersonEducationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/prs/v1/persons/{personId}/educations")
public class PersonEducationController {

    private final PersonEducationService personEducationService;
    private final Mapper<PersonEducation, PersonEducationDto> mapper = new Mapper<>(PersonEducationService::toDto);

    @GetMapping
    public ResponseEntity<List<PersonEducationDto>> findAll(
            @RequestHeader(value = "personId") Long personId) {
        return ResponseEntity.ok(mapper.map(personEducationService.findAllByPersonId(personId)));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonEducationDto> findById(
            @RequestHeader(value = "personId") Long personId,
            @PathVariable Long id) throws PersonEducationNotFoundException {
        return ResponseEntity.ok(mapper.map(personEducationService.findById(id)));
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
        personEducationService.deleteById(id);
        return ResponseEntity.ok("Person deleted.");
    }


}
