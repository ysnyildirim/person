package com.yil.person.repository;

import com.yil.person.model.PersonAddress;
import com.yil.person.model.PersonEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonEmailRepository extends JpaRepository<PersonEmail, Long> {
    Page<PersonEmail> findAllByPersonIdAndDeletedTimeIsNull(Pageable pageable, Long personId);
}
