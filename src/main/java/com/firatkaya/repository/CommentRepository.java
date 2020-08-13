package com.firatkaya.repository;


import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.firatkaya.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String>  {

	
	@Query(value ="SELECT * FROM comment c WHERE c.comment_id = :commentId", nativeQuery = true)
	Comment findByCommentId(@Param("commentId") String commentId );
	
	@Modifying
	@Transactional
	@Query(value ="DELETE FROM comment  WHERE comment_id = :commentId", nativeQuery = true)
	void deleteByCommentsId(@Param("commentId") String commentId);
	
	
	@Query(value ="SELECT * FROM comment ", nativeQuery = true)
	List<Comment> getAll();
	
	
	@Query(value="SELECT * FROM comment where "
			+ "comment_message LIKE (CONCAT(:keyword,'%')) or "
			+ "comment_message LIKE (CONCAT('%',:keyword)) or "
			+ "comment_message LIKE (CONCAT('%',:keyword,'%')) or "
			+ "user_id LIKE (CONCAT(:keyword,'%')) or "
			+ "user_id LIKE (CONCAT('%',:keyword)) or "
			+ "user_id LIKE (CONCAT('%',:keyword,'%')) LIMIT 10",nativeQuery = true)		
	<T> Collection<T> searchCommentIDandBody(@Param("keyword") String keyword,Class<T> type);

	@Modifying 
	@Query(value = "UPDATE comment SET user_profil_photo = :path  WHERE user_name = :username",nativeQuery = true)
	void updateUserPhoto(@Param("username") String username,@Param("path") String path);

	
}
