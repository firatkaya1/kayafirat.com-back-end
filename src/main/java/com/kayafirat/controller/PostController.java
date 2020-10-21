package com.kayafirat.controller;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kayafirat.entity.Post;
import com.kayafirat.model.StaticsViews;
import com.kayafirat.model.projection.PostExceptr;
import com.kayafirat.model.projection.PostExceptrSearch;
import com.kayafirat.service.PostService;
import com.kayafirat.service.StaticsViewService;

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
    public ResponseEntity<?> getByTitle(@RequestParam String title) {
        return ResponseEntity.ok(postService.getByPostTitle(title));
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
