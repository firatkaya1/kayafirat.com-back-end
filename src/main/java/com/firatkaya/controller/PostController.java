package com.firatkaya.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.firatkaya.entity.Post;
import com.firatkaya.model.StaticsViews;
import com.firatkaya.model.projection.PostExceptr;
import com.firatkaya.model.projection.PostExceptrSearch;
import com.firatkaya.service.PostService;
import com.firatkaya.service.StaticsViewService;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostController {

    private final PostService postService;
    private final StaticsViewService staticViewService;

    @GetMapping(value = "/{pagenumber}/{pageSize}/sorted/{sortedBy}/orderby/{orderBy}")
    public ResponseEntity<Page<PostExceptr>> getAllPosts(
            @PathVariable(value = "pagenumber") int pageNumber,
            @PathVariable(value = "pageSize") int pageSize,
            @PathVariable(value = "sortedBy") String sortedBy,
            @PathVariable(value = "orderBy") String orderBy) {


        return ResponseEntity.ok(postService.getAllPostPagenable(pageNumber, pageSize, sortedBy, orderBy));
    }

    @GetMapping(value = "postId/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable(value = "postId") String postId) {
        return ResponseEntity.ok(postService.getByPostId(postId));
    }

    @GetMapping(value = "postTitle/{postTitle}")
    public ResponseEntity<List<Post>> getPostTitle(@PathVariable(value = "postTitle") String postTitle) {
        List<Post> myPost = new ArrayList<>();
        myPost.add(postService.getByPostTitle(postTitle));
        return ResponseEntity.ok(myPost);
    }

    @GetMapping(value = "postTag/{postTag}")
    public ResponseEntity<Collection<?>> getPostTag(@PathVariable(value = "postTag") String postTag) {

        return ResponseEntity.ok(postService.getByPostTag(postTag));
    }

    @GetMapping(value = "lastposts/{postnumber}/{ordertype}")
    public ResponseEntity<Collection<?>> lastpost(@PathVariable(value = "postnumber") int limit,
                                                  @PathVariable(value = "ordertype") String ordertype) {

        return ResponseEntity.ok(postService.lastPost(limit, ordertype));
    }

    @GetMapping(value = "search/{keyword}/{pagenumber}/{pageSize}/sorted/{sortedBy}/orderby/{orderBy}")
    public ResponseEntity<Page<PostExceptrSearch>> searchPost(@PathVariable(value = "keyword") String keyword,
                                                              @PathVariable(value = "pagenumber") int pageNumber,
                                                              @PathVariable(value = "pageSize") int pageSize,
                                                              @PathVariable(value = "sortedBy") String sortedBy,
                                                              @PathVariable(value = "orderBy") String orderBy) {

        return ResponseEntity.ok(postService.searchPost(keyword, pageNumber, pageSize, sortedBy, orderBy));
    }

    @PostMapping(value = "/updateview")
    public ResponseEntity<?> updateMaxView(@RequestBody StaticsViews staticview) {
        staticViewService.addStaticViews(staticview);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> savePost(@RequestBody Post post) {
        postService.savePost(post);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
