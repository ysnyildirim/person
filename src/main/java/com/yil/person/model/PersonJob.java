package com.yil.person.model;

import com.yil.person.base.IEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(schema = "PRS", name = "PERSON_JOB")
public class PersonJob implements IEntity {
    @EmbeddedId
    private Pk id;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "PERSON_ID", nullable = false)
        private Long personId;
        @Column(name = "JOB_ID", nullable = false)
        private Long jobId;
    }
}
