package com.kayafirat.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kayafirat.model.StaticsViews;

@Repository
public interface StaticsViewRepository extends JpaRepository<StaticsViews, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM staticsview WHERE view_date < (NOW() - INTERVAL 15 MINUTE)", nativeQuery = true)
    void updateAllRows();

    @Query(value = "SELECT EXISTS(SELECT * FROM staticsview WHERE ip_address=:ipAddress and post_id = :postId);", nativeQuery = true)
    int existsByIpAddressandPostId(@Param("ipAddress") String ipAddress, @Param("postId") String postId);

    @Query(value = "SELECT EXISTS(SELECT * FROM staticsview WHERE ip_address=:ipAddress and post_id = :postId and temporary_code=:temporaryCode);", nativeQuery = true)
    int exitstByAllValues(@Param("ipAddress") String ipAddress, @Param("postId") String postId, @Param("temporaryCode") String temporaryCode);

    boolean existsByIpAddress(String ipAddress);

    boolean existsByTemporaryCode(String temporaryCode);

}
