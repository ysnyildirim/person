package com.yil.person.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonPhoneDto {
    private Long number;
    private Long phoneTypeId;
}
