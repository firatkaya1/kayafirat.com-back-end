package com.kayafirat.service.Impl;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.kayafirat.entity.PostSeo;
import com.kayafirat.validation.constraint.ExistsPostId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kayafirat.entity.Post;
import com.kayafirat.exceptions.customExceptions.UnknownOrderedRequestException;
import com.kayafirat.model.projection.PostExceptr;
import com.kayafirat.model.projection.PostExceptrSearch;
import com.kayafirat.repository.PostRepository;
import com.kayafirat.service.PostService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IPostService implements PostService {

    private final PostRepository postRepository;

    @Override
    public List<PostExceptr> getAllPost() {
        return postRepository.findAllProjectBy();
    }

    @Override
    public Page<PostExceptr> getAllPostPagenable(int pageNumber, int pageSize, String sortedBy, String orderBy) {
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
    public Post getByPostId(@ExistsPostId String postId) {
        return postRepository.findByPostIdOrderByPostTimeAsc(postId);
    }

    @Override
    public Post savePost(Post _post) {
        PostSeo postSeo = new PostSeo();
        _post.setPostId(UUID.randomUUID().toString());
        postSeo.setPostId(_post.getPostId());
        _post.setPostSeo(postSeo);
        return postRepository.save(_post);
    }

    @Override
    public void deletePost(@ExistsPostId String postId) {
        postRepository.deleteById(postId);
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
