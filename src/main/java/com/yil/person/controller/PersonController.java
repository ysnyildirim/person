package com.yil.person.controller;

import com.yil.person.base.ApiConstant;
import com.yil.person.base.Mapper;
import com.yil.person.base.PageDto;
import com.yil.person.dto.CreatePersonDto;
import com.yil.person.dto.PersonDto;
import com.yil.person.exception.EducationNotFoundException;
import com.yil.person.exception.GenderNotFoundException;
import com.yil.person.exception.PersonNotFoundException;
import com.yil.person.model.Person;
import com.yil.person.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/prs/v1/persons")
public class PersonController {

    private final PersonService personService;
    private final Mapper<Person, PersonDto> mapper = new Mapper<>(PersonService::toDto);

    @GetMapping
    public ResponseEntity<PageDto<PersonDto>> findAll(
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE) int page,
            @RequestParam(required = false, defaultValue = ApiConstant.PAGE_SIZE) int size) {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 1000)
            size = 1000;
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(mapper.map(personService.findAll(pageable)));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDto> findById(@PathVariable Long id) throws PersonNotFoundException {
        return ResponseEntity.ok(mapper.map(personService.findById(id)));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                                         @Valid @RequestBody CreatePersonDto dto) throws GenderNotFoundException, EducationNotFoundException {
        personService.save(dto, authenticatedUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Kişi eklendi");
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
                                         @PathVariable Long id) throws PersonNotFoundException {
        personService.deleteById(id);
        return ResponseEntity.ok("Person deleted.");
    }
}
