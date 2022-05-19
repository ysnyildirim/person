package com.yil.person.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonEmailDto {
    private Long id;
    private Long personId;
    private String address;
    private Long emailTypeId;
}
