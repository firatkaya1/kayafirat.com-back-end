package com.firatkaya.service.Impl;

import java.util.*;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.firatkaya.entity.Comment;
import com.firatkaya.entity.Post;
import com.firatkaya.entity.User;
import com.firatkaya.exceptions.customExceptions.CommentNotFoundException;
import com.firatkaya.exceptions.customExceptions.PostNotFoundException;
import com.firatkaya.model.excep.CommentExceptr;
import com.firatkaya.repository.CommentRepository;
import com.firatkaya.repository.PostRepository;
import com.firatkaya.service.CommentService;
import com.firatkaya.service.UserService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final Environment env;

    @Override
    public List<CommentExceptr> getAllComments() {
        return commentRepository.findAllProject();
    }

    @Override
    @Cacheable(cacheNames = "allComments", key = "#postId")
    public List<Comment> getAllComments(String postId) {
        if (!postRepository.existsByPostId(postId))
            throw new PostNotFoundException(postId);

        return commentRepository.findAll();
    }

    @Transactional
    @Override
    @CacheEvict(value = "PostTitle", allEntries=true)
    public Comment saveComment(Comment comment, String postId) {
        Post post = postRepository.findByPostId(postId);

        comment.setCommentId(UUID.randomUUID().toString());
        comment.setPost(post);
        if (comment.getUsername().equals("Anonymous")) {
            comment.setUserProfilePhoto(env.getProperty("user.default.profile-photo"));
        } else {
            User user = userService.getUserByEmail(comment.getUsername());
            comment.setUserProfilePhoto(user.getUserProfilePhoto());
            comment.setUsername(user.getUserName());
        }

        postRepository.increaseTotalComment(postId);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    @CacheEvict(value = "PostTitle", allEntries=true)
    public boolean updateComment(HashMap<String,String> request) {
        if (!commentRepository.existsById(request.get("commentId")))
            throw new CommentNotFoundException(request.get("commentId"));
        commentRepository.updateUserComment(request.get("commentMessage"),request.get("commentId"));
        return true;
    }

    @Transactional
    @Override
    @CacheEvict(value = "PostTitle", allEntries=true)
    public boolean deleteComment(String commentId,String postId) {
        if (!commentRepository.existsById(commentId))
            throw new CommentNotFoundException(commentId);

        postRepository.decreaseTotalComment(postId);
        commentRepository.deleteByCommentsId(commentId);
        return true;
    }

    @Override
    public Collection<?> searchComment(String keyword) {
        return commentRepository.searchCommentIDandBody(keyword, CommentExceptr.class);
    }

}
