package com.yil.person.model;

import com.yil.person.base.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "PersonAddress")
public class PersonAddress extends AbstractEntity {
    @Id
    @SequenceGenerator(name = "PersonAddress_Sequence_Generator",
            sequenceName = "Seq_PersonAddress",
            allocationSize = 1)
    @GeneratedValue(generator = "PersonAddress_Sequence_Generator")
    @Column(name = "Id")
    private Long id;

    @Column(name = "PersonId")
    private Long personId;

    @Column(name = "CountryId")
    private Long countryId;

    @Column(name = "CityId")
    private Long cityId;

    @Column(name = "DistrictId")
    private Long districtId;

    @Column(name = "StreetId")
    private Long streetId;

    @Column(name = "ExteriorDoorId")
    private Long exteriorDoorId;

    @Column(name = "InteriorDoorId")
    private Long interiorDoorId;
}
