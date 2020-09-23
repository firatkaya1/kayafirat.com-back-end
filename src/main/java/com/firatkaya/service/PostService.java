package com.firatkaya.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.firatkaya.entity.Post;
import com.firatkaya.model.excep.PostExceptr;
import com.firatkaya.model.excep.PostExceptrSearch;

public interface PostService {

    List<PostExceptr> getAllPost();

    Page<PostExceptr> getAllPost(int pageNumber, int pageSize, String sortedBy, String orderBy);

    Post getPost(String postId);

    Post savePost(Post post);

    Post deletePost(String postId);

    Post updatePost(Post post);


    Post getByPostTitle(String postTitle);

    Collection<?> lastPost(int limit, String ordertype);

    Page<PostExceptrSearch> searchPost(String keyword, int pageNumber, int pageSize, String sortedBy, String orderBy);

    Collection<?> getByPostTag(String postTag);

}
