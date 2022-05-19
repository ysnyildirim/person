package com.yil.person.repository;

import com.yil.person.model.AddressType;
import com.yil.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, Long> {
    List<AddressType> findAllByDeletedTimeIsNull();

}
