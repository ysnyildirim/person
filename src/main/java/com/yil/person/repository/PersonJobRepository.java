package com.yil.person.repository;

import com.yil.person.model.PersonJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

//public interface PersonJobRepository extends JpaRepository<PersonJob, PersonJob.Pk> {

public interface PersonJobRepository extends JpaRepository<PersonJob, PersonJob.Pk> {

    @Query(nativeQuery = true,
            value = "Select * from prs.person_job where person_id = :personId")
    List<PersonJob> findAllByPersonId(@Param("personId") Long personId);


}
