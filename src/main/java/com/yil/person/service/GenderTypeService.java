package com.yil.person.service;

import com.yil.person.dto.GenderTypeDto;
import com.yil.person.exception.GenderNotFoundException;
import com.yil.person.model.GenderType;
import com.yil.person.repository.GenderTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenderTypeService {

    private final GenderTypeRepository genderTypeRepository;
    //  private final GenderTypeDto genderTypeDto;

    public static GenderTypeDto toDto(GenderType genderType) throws NullPointerException {
        if (genderType == null)
            throw new NullPointerException("Gender type is null");
        GenderTypeDto dto = new GenderTypeDto();
        dto.setId(genderType.getId());
        dto.setName(genderType.getName());
        return dto;
    }

    public List<GenderType> findAll() {
        return genderTypeRepository.findAll();
    }

    public GenderType findById(Integer id) throws GenderNotFoundException {
        return genderTypeRepository.findById(id).orElseThrow(GenderNotFoundException::new);


    }
}
