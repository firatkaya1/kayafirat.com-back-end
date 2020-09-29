package com.firatkaya.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.firatkaya.entity.Post;
import com.firatkaya.model.projection.PostExceptr;
import com.firatkaya.model.projection.PostExceptrSearch;

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
