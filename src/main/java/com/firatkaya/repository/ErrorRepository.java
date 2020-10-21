package com.firatkaya.repository;

import com.firatkaya.entity.Error;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository extends JpaRepository<Error,Long> {

    Error findByErrorId(Long id);
}
