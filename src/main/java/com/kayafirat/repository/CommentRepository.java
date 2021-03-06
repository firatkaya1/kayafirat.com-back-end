package com.kayafirat.repository;


import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.kayafirat.model.projection.CommentDetailExcept;
import com.kayafirat.model.projection.CommentExceptr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kayafirat.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query(value = "SELECT comment_id,comment_message,comment_time,is_hide,user_name FROM comment ", nativeQuery = true)
    List<CommentExceptr> findAllProject();

    @Query(value = "SELECT comment_id,comment_message,comment_time,user_name,is_hide,is_updated,user_profil_photo,comment_updated_time FROM comment where comment_id=:commentId", nativeQuery = true)
    CommentDetailExcept findAllCommentDetails(String commentId);

    @Query(value = "SELECT * FROM comment c WHERE c.comment_id = :commentId", nativeQuery = true)
    Comment findByCommentId(@Param("commentId") String commentId);

    @Modifying
    @Transactional
    @Query(value ="DELETE FROM comment where comment_id=:commentId", nativeQuery = true)
    void deleteByCommentsId(@Param("commentId") String commentId);

    @Query(value = "SELECT * FROM comment ", nativeQuery = true)
    List<Comment> getAll();

    @Query(value = "SELECT * FROM comment where "
            + "comment_message LIKE (CONCAT(:keyword,'%')) or "
            + "comment_message LIKE (CONCAT('%',:keyword)) or "
            + "comment_message LIKE (CONCAT('%',:keyword,'%')) or "
            + "user_id LIKE (CONCAT(:keyword,'%')) or "
            + "user_id LIKE (CONCAT('%',:keyword)) or "
            + "user_id LIKE (CONCAT('%',:keyword,'%')) LIMIT 10", nativeQuery = true)
    <T> Collection<T> searchCommentIDandBody(@Param("keyword") String keyword, Class<T> type);

    @Modifying
    @Query(value = "UPDATE comment SET user_profil_photo = :path  WHERE user_name = :username", nativeQuery = true)
    void updateUserPhoto(@Param("username") String username, @Param("path") String path);

    @Modifying
    @Query(value = "UPDATE comment SET comment_message = :commentMessage, comment_updated_time = (SELECT CURRENT_TIMESTAMP())  ,is_updated = 1 WHERE comment_id = :id", nativeQuery = true)
    void updateUserComment(@Param("commentMessage") String commentMessage, @Param("id") String id);
}
