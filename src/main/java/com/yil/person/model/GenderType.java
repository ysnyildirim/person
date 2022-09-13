package com.yil.person.model;


import com.yil.person.base.IEntity;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode
@Entity
@Data
@Table(name = "GENDER_TYPE")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenderType implements IEntity {
    @Id
    @SequenceGenerator(name = "GENDER_TYPE_SEQUENCE_GENERATOR",
            sequenceName = "SEQ_GENDER_TYPE_ID",
            allocationSize = 1)
    @GeneratedValue(generator = "GENDER_TYPE_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;
}

