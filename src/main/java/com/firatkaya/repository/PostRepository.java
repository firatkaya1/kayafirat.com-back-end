package com.firatkaya.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.firatkaya.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,String>  {
	
	@Modifying
	@Query(value = "UPDATE post SET post_max_comment = post_max_comment + 1 WHERE post_id = :postId",nativeQuery = true)
	void updateMaxComment(@Param("postId") String postId);
	
	boolean existsByPostId(String postId);
	
	Post findByPostIdOrderByPostTimeAsc(String postId);
	
	Post findByPostId(String postId);
	
	Post findByPostTitle(String postTitle);
	
	@Query(value ="SELECT * FROM post order by post_time DESC LIMIT :lim", nativeQuery = true)
	<T> Collection<T> orderByDesc(@Param("lim") int limit,Class<T> type);
	
	@Query(value ="SELECT * FROM post order by post_time ASC LIMIT :lim", nativeQuery = true)
	<T> Collection<T> orderByAsc(@Param("lim") int limit,Class<T> type);
	
	@Query(value="SELECT * FROM post where "
			+ "post_tag LIKE (CONCAT(:keyword,'%')) or "
			+ "post_tag LIKE (CONCAT('%',:keyword)) or "
			+ "post_tag LIKE (CONCAT('%',:keyword,'%')) or "
			+ "post_header LIKE (CONCAT(:keyword,'%')) or "
			+ "post_header LIKE (CONCAT('%',:keyword)) or "
			+ "post_header LIKE (CONCAT('%',:keyword,'%')) or "
			+ "post_title LIKE (CONCAT(:keyword,'%')) or "
			+ "post_title LIKE (CONCAT('%',:keyword)) or "
			+ "post_title LIKE (CONCAT('%',:keyword,'%')) LIMIT 10 ",nativeQuery = true)		
	<T> Collection<T> searchByTitleHeaderTag(@Param("keyword") String keyword,Class<T> type);
		
	@Modifying
	@Query(value = "UPDATE post SET post_max_view = post_max_view + 1 WHERE post_id = :postId",nativeQuery = true)
	void updatePageView(@Param("postId") String postId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
