package com.kayafirat.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.kayafirat.entity.Post;
import com.kayafirat.model.projection.PostExceptr;
import com.kayafirat.model.projection.PostExceptrSearch;

public interface PostService {

    List<PostExceptr> getAllPost();

    Page<PostExceptr> getAllPostPagenable(int pageNumber, int pageSize, String sortedBy, String orderBy);

    Post getByPostId(String postId);

    Post savePost(Post post);

     void deletePost(String postId);

    Post updatePost(Post post);

    Post getByPostTitle(String postTitle);

    Collection<?> lastPost(int limit, String ordered);

    Page<PostExceptrSearch> searchPost(String keyword, int pageNumber, int pageSize, String sortedBy, String orderBy);

    Collection<?> getByPostTag(String postTag);

}
