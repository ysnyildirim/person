package com.yil.person.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonEducationDto {
    private Long id;
    private Long personId;
    private Integer educationId;
    private Date startDate;
    private Date finishDate;
}
