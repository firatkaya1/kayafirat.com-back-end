package com.firatkaya.service.Impl;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.firatkaya.entity.PostSeo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.firatkaya.entity.Post;
import com.firatkaya.exceptions.PostNotFoundException;
import com.firatkaya.exceptions.UnknownOrderedRequestException;
import com.firatkaya.model.excep.PostExceptr;
import com.firatkaya.model.excep.PostExceptrSearch;
import com.firatkaya.repository.PostRepository;
import com.firatkaya.service.PostService;

@Service
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImp(PostRepository postRepository ){
        this.postRepository = postRepository;
    }

    @Override
    public List<PostExceptr> getAllPost() {
        return postRepository.findAllProjectBy();
    }

    @Override
    public Page<PostExceptr> getAllPost(int pageNumber, int pageSize, String sortedBy, String orderBy) {
        Sort sort;
        if (orderBy.equals("asc"))
            sort = Sort.by(sortedBy).ascending();
        else
            sort = Sort.by(sortedBy).descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        return postRepository.findAllProjectedBy(pageable);
    }

    @Override
    @Cacheable(cacheNames = "PostId", key = "#postId")
    public Post getPost(String postId) {
        if (!postRepository.existsByPostId(postId))
            throw new PostNotFoundException(postId);

        return postRepository.findByPostIdOrderByPostTimeAsc(postId);
    }

    @Override
    public Post savePost(Post _post) {
        Post post = new Post();
        PostSeo postSeo = new PostSeo();
        post.setPostId(UUID.randomUUID().toString());
        post.setPostSeo(postSeo);
        return postRepository.save(post);
    }

    @Override
    public Post deletePost(String postId) {
        return null;
    }

    @Override
    public Post updatePost(Post post) {
        return null;
    }

    @Override
    public Collection<PostExceptr> lastPost(int limit, String ordered) {
        if (ordered.toLowerCase().equals("desc"))
            return postRepository.orderByDesc(limit, PostExceptr.class);
        else if (ordered.toLowerCase().equals("asc"))
            return postRepository.orderByAsc(limit, PostExceptr.class);
        else throw new UnknownOrderedRequestException();
    }

    @Override
    public Page<PostExceptrSearch> searchPost(String keyword, int pageNumber, int pageSize, String sortedBy, String orderBy) {
        Sort sort;
        if (orderBy.equals("asc"))
            sort = Sort.by(sortedBy).ascending();
        else
            sort = Sort.by(sortedBy).descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        return postRepository.searchByTitleHeaderTag(pageable, keyword);
    }

    @Override
    @Cacheable(cacheNames = "PostTitle", key = "#postTitle")
    public Post getByPostTitle(String postTitle) {
        return postRepository.findByPostTitle(postTitle);
    }

    @Override
    public Collection<?> getByPostTag(String postTag) {
        return postRepository.findByAllPostTag(postTag, PostExceptr.class);
    }

}
