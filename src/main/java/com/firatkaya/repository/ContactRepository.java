package com.firatkaya.repository;

import com.firatkaya.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {

    Contact findByContactId(Long id);

    @Query(value = "UPDATE contact SET is_read=1 where contact_id = :id",nativeQuery = true)
    Contact updateReadStatus(@Param("id") Long id);
}
