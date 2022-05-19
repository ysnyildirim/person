package com.yil.person.model;

import com.yil.person.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "PersonPhone")
public class PersonPhone extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "PersonPhone_Sequence_Generator",
            sequenceName = "Seq_PersonPhone",
            allocationSize = 1)
    @GeneratedValue(generator = "PersonPhone_Sequence_Generator")
    @Column(name = "Id")
    private Long id;
    @Column(name = "PersonId", nullable = false)
    private Long personId;
    @Column(name = "PhoneId", nullable = false)
    private Long number;
    @Column(name = "PhoneTypeId", nullable = false)
    private Long phoneTypeId;

}
