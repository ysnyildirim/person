package com.yil.person.controller;

import com.yil.person.dto.GenderTypeDto;
import com.yil.person.exception.GenderNotFoundException;
import com.yil.person.model.GenderType;
import com.yil.person.service.GenderTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/prs/v1/gender-types")
public class GenderTypeController {


    private final GenderTypeService genderTypeService;

    @GetMapping
    public ResponseEntity<List<GenderTypeDto>> findAll() {
        List<GenderType> genderTypes = genderTypeService.findAll();
        List<GenderTypeDto> dtos = new ArrayList<>();
        for (GenderType genderType : genderTypes)
            dtos.add(GenderTypeService.toDto(genderType));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GenderTypeDto> findById(
            @PathVariable Integer id) throws GenderNotFoundException {
        GenderType genderType = genderTypeService.findById(id);
        GenderTypeDto dto = GenderTypeService.toDto(genderType);
        return ResponseEntity.ok(dto);
    }
}

