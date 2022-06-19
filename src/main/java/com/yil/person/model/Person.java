package com.yil.person.model;

import com.yil.person.base.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PERSON")
public class Person extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "PERSON_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_PERSON_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "PERSON_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "FIRST_NAME", nullable = false, length = 1000)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false, length = 1000)
    private String lastName;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BIRTH_DATE")
    private Date birthDate;
    @Column(name = "IDENTIFICATION_NUMBER")
    private Long identificationNumber;
    @Column(name = "CONTACT_ID")
    private Long contactId;
}
