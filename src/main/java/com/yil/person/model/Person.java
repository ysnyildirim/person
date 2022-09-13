package com.yil.person.model;

import com.yil.person.base.IEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "PRS",
        name = "PERSON",
        indexes = {
                @Index(name = "IDX_PERSON_IDENTIFICATION_NUMBER", columnList = "IDENTIFICATION_NUMBER")
        })
public class Person implements IEntity {
    @Id
    @SequenceGenerator(name = "PERSON_SEQUENCE_GENERATOR",
            schema = "PRS",
            sequenceName = "SEQ_PERSON_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "PERSON_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;
    @Column(name = "FIRST_NAME", nullable = false, length = 1000)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false, length = 1000)
    private String lastName;
    @ColumnDefault(value = "0")
    @Column(name = "GENDER_ID", nullable = false)
    private Integer genderId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BIRTH_DATE")
    private Date birthDate;
    @Column(name = "IDENTIFICATION_NUMBER")
    private Long identificationNumber;
    @Column(name = "CONTACT_ID")
    private Long contactId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME")
    private Date createdTime;
    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;
}
