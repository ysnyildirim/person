package com.yil.person.controller;

import com.yil.person.base.Mapper;
import com.yil.person.dto.GenderTypeDto;
import com.yil.person.exception.GenderNotFoundException;
import com.yil.person.model.GenderType;
import com.yil.person.service.GenderTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/prs/v1/gender-types")
public class GenderTypeController {
    private final Mapper<GenderType, GenderTypeDto> mapper = new Mapper<>(GenderTypeService::toDto);
    private final GenderTypeService genderTypeService;

    @Cacheable("gender-type")
    @Operation(summary = "Tüm cinsiyet tiplerini getirir")
    @GetMapping
    public ResponseEntity<List<GenderTypeDto>> findAll() {
        return ResponseEntity.ok(mapper.map(genderTypeService.findAll()));
    }

    @Operation(summary = "Id bazlı cinsiyet bilgilerini getirir")
    @GetMapping(value = "/{id}")
    public ResponseEntity<GenderTypeDto> findById(@PathVariable Integer id) throws GenderNotFoundException {
        return ResponseEntity.ok(mapper.map(genderTypeService.findById(id)));
    }
}
