package com.yil.person.model;

import com.yil.person.base.IEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode
@Entity
@Data
@Table(name = "EDUCATION_PERSON",
        schema = "PRS",
        indexes = {
                @Index(name = "IDX_EDUCATION_PERSON_PERSON_ID", columnList = "PERSON_ID"),
                @Index(name = "IDX_EDUCATION_PERSON_EDUCATION_ID", columnList = "EDUCATION_ID")
        }

)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonEducation implements IEntity {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "PERSON_ID", nullable = false)
    private Long personId;
    @Column(name = "EDUCATION_ID", nullable = false)
    private Integer educationId;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "START_DATE", nullable = false)
    private Date startDate;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "END_DATE")
    private Date finishDate;

}
