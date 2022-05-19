package com.yil.person.repository;

import com.yil.person.model.EmailType;
import com.yil.person.model.PhoneType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailTypeRepository extends JpaRepository<EmailType, Long> {
    List<EmailType> findAllByDeletedTimeIsNull();

}
