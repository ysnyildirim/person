package com.yil.person.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonDto {
    @NotBlank
    @Length(min = 1, max = 1000)
    private String firstName;
    @NotBlank
    @Length(min = 1, max = 1000)
    private String lastName;
    private Date birthDate;
    private Long identificationNumber;
    private Long contactId;
    private Integer genderId;
}
