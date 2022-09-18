package com.yil.person.repository;

import com.yil.person.model.PersonEducation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonEducationRepository extends JpaRepository<PersonEducation, Long> {

    List<PersonEducation> findAllByPersonId(long personId);
}
