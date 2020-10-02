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
@RequestMapping("/v1/post")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostController {

    private final PostService postService;
    private final StaticsViewService staticViewService;

    @GetMapping
    public ResponseEntity<Page<PostExceptr>> getAllPosts(@RequestParam int page, @RequestParam int size, @RequestParam String sort, @RequestParam String order) {
        return ResponseEntity.ok(postService.getAllPostPagenable(page, size, sort, order));
    }

    @GetMapping(value = "/id")
    public ResponseEntity<Post> getById(@RequestParam String id) {
        return ResponseEntity.ok(postService.getByPostId(id));
    }

    @GetMapping(value = "/title")
    public ResponseEntity<List<Post>> getByTitle(@RequestParam String title) {
        List<Post> myPost = new ArrayList<>();
        myPost.add(postService.getByPostTitle(title));
        return ResponseEntity.ok(myPost);
    }

    @GetMapping(value = "/tag")
    public ResponseEntity<Collection<?>> getByTag(@RequestParam String tag) {
        return ResponseEntity.ok(postService.getByPostTag(tag));
    }

    @GetMapping(value = "/last")
    public ResponseEntity<Collection<?>> getPostByOrder(@RequestParam int limit, @RequestParam String order) {
        return ResponseEntity.ok(postService.lastPost(limit, order));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<PostExceptrSearch>> searchPost(@RequestParam String keyword, @RequestParam int page, @RequestParam int size, @RequestParam String sort, @RequestParam String order) {
        return ResponseEntity.ok(postService.searchPost(keyword, page, size, sort, order));
    }

    @PostMapping(value = "/view")
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
