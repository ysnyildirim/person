package com.yil.person.model;

import com.yil.person.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "PersonEmail")
public class PersonEmail extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "PersonEmail_Sequence_Generator",
            sequenceName = "Seq_PersonEmail",
            allocationSize = 1)
    @GeneratedValue(generator = "PersonEmail_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "PersonId", nullable = false)
    private Long personId;
    @Column(name = "Address", nullable = false)
    private String address;
    @Column(name = "EmailTypeId", nullable = false)
    private Long emailTypeId;

}