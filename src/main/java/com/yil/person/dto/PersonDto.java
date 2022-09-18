package com.yil.person.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Long identificationNumber;
    private int genderTypeId;
    private Long contactId;
    private Integer jobId;
}
