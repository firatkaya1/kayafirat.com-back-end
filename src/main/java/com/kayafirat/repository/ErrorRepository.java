package com.kayafirat.repository;

import com.kayafirat.entity.Error;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorRepository extends JpaRepository<Error,Long> {

    Error findByErrorId(Long id);

    @Modifying
    @Query(value = "UPDATE error SET is_read=1 where error_id = :id",nativeQuery = true)
    void updateReadStatus(@Param("id") Long id);
}
