package com.yil.person.repository;

import com.yil.person.model.PersonEmail;
import com.yil.person.model.PersonPhone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonPhoneRepository extends JpaRepository<PersonPhone, Long> {
    Page<PersonPhone> findAllByAndPersonIdAndDeletedTimeIsNull(Pageable pageable,Long personId);
}
