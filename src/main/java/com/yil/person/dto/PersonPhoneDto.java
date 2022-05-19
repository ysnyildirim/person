package com.yil.person.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonPhoneDto {
    private Long id;
    private Long personId;
    private Long number;
    private Long phoneTypeId;
}
