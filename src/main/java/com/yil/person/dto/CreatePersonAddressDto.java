package com.yil.person.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonAddressDto {
    private Long countryId;
    private Long cityId;
    private Long districtId;
    private Long streetId;
    private Long exteriorDoorId;
    private Long interiorDoorId;
}
