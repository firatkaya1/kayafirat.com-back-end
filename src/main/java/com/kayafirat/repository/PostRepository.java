package com.kayafirat.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kayafirat.entity.Post;
import com.kayafirat.model.projection.PostExceptr;
import com.kayafirat.model.projection.PostExceptrSearch;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    @Query(value = "SELECT post_id,post_tag,post_title,post_header,post_time,post_max_view FROM firatkayablog.post", nativeQuery = true)
    List<PostExceptr> findAllProjectBy();

    @Query(value = "SELECT post_id,post_tag,post_title,post_header,post_time,post_max_view,post_max_comment FROM firatkayablog.post", nativeQuery = true)
    Page<PostExceptr> findAllProjectedBy(Pageable page);

    boolean existsByPostId(String postId);

    Post findByPostIdOrderByPostTimeAsc(String postId);

    Post findByPostId(String postId);

    Post findByPostTitle(String postTitle);

    @Query(value = "SELECT * FROM post order by post_time DESC LIMIT :lim", nativeQuery = true)
    <T> Collection<T> orderByDesc(@Param("lim") int limit, Class<T> type);

    @Query(value = "SELECT * FROM post order by post_time ASC LIMIT :lim", nativeQuery = true)
    <T> Collection<T> orderByAsc(@Param("lim") int limit, Class<T> type);

    @Query(value = "SELECT * FROM post where post_tag = :postTag", nativeQuery = true)
    <T> Collection<T> findByAllPostTag(@Param("postTag") String postTag, Class<T> type);

    @Query(value = "SELECT * FROM post where "
            + "post_tag LIKE (CONCAT(:keyword,'%')) or "
            + "post_tag LIKE (CONCAT('%',:keyword)) or "
            + "post_tag LIKE (CONCAT('%',:keyword,'%')) or "
            + "post_header LIKE (CONCAT(:keyword,'%')) or "
            + "post_header LIKE (CONCAT('%',:keyword)) or "
            + "post_header LIKE (CONCAT('%',:keyword,'%')) or "
            + "post_title LIKE (CONCAT(:keyword,'%')) or "
            + "post_title LIKE (CONCAT('%',:keyword)) or "
            + "post_title LIKE (CONCAT('%',:keyword,'%'))", nativeQuery = true)
    Page<PostExceptrSearch> searchByTitleHeaderTag(Pageable page, @Param("keyword") String keyword);

    @Modifying
    @Query(value = "UPDATE post SET post_max_view = post_max_view + 1 WHERE post_id = :postId", nativeQuery = true)
    void updatePageView(@Param("postId") String postId);


}
