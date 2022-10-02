package com.yil.person.controller;

import com.yil.person.base.ApiConstant;
import com.yil.person.base.Mapper;
import com.yil.person.dto.PersonJobDto;
import com.yil.person.dto.PersonJobRequest;
import com.yil.person.exception.PersonNotFoundException;
import com.yil.person.model.PersonJob;
import com.yil.person.service.PersonJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/prs/v1/persons/{personId}/jobs")
public class PersonJobController {
    private final PersonJobService personJobService;
    private final Mapper<PersonJob, PersonJobDto> mapper = new Mapper<>(PersonJobService::toDto);

    @GetMapping
    public ResponseEntity<List<PersonJobDto>> findAllByPersonId(@PathVariable(value = "personId") Long personId) {
        return ResponseEntity.ok(mapper.map(personJobService.findAllByPersonId(personId)));
    }

    @PostMapping
    public ResponseEntity save(@RequestHeader(value = ApiConstant.AUTHENTICATED_USER_ID) Long authenticatedUserId,
                               @PathVariable(value = "personId") Long personId,
                               @Valid @RequestBody PersonJobRequest request) throws PersonNotFoundException {
        personJobService.save(request, personId, authenticatedUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added");
    }
}
