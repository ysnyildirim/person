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
@Table(name = "Person")
public class Person extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "Person_Sequence_Generator",
            sequenceName = "Seq_Person",
            allocationSize = 1)
    @GeneratedValue(generator = "Person_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "FirstName", nullable = false, length = 1000)
    private String firstName;
    @Column(name = "LastName", nullable = false, length = 1000)
    private String lastName;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BirthDate")
    private Date birthDate;
    @Column(name = "IdentificationNumber")
    private Long identificationNumber;
    @Column(name = "ContactId")
    private Long contactId;
}
