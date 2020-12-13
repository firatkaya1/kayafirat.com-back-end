package com.kayafirat.service.Impl;

import java.util.*;

import javax.transaction.Transactional;

import com.kayafirat.model.projection.CommentDetailExcept;
import com.kayafirat.repository.UserRepository;
import com.kayafirat.validation.constraint.ExistsCommentId;
import com.kayafirat.validation.constraint.ExistsPostId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.kayafirat.entity.Comment;
import com.kayafirat.entity.Post;
import com.kayafirat.entity.User;
import com.kayafirat.model.projection.CommentExceptr;
import com.kayafirat.repository.CommentRepository;
import com.kayafirat.repository.PostRepository;
import com.kayafirat.service.CommentService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ICommentService implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Environment env;

    @Override
    public CommentDetailExcept getAllCommentsDetails(String id) {
        return commentRepository.findAllCommentDetails(id);
    }

    @Override
    public List<CommentExceptr> getAllComments() {
        return commentRepository.findAllProject();
    }

    @Override
    @Cacheable(cacheNames = "allComments", key = "#postId")
    public List<Comment> getAllComments(@ExistsPostId String postId) {
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
            User user = userRepository.findByUserName(comment.getUsername());
            comment.setUserProfilePhoto(user.getUserProfilePhoto());
            comment.setUsername(user.getUserName());
        }

        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    @CacheEvict(value = "PostTitle", allEntries=true)
    public void updateComment(@ExistsCommentId  HashMap<String,String> request) {
        commentRepository.updateUserComment(request.get("commentMessage"),request.get("commentId"));
    }

    @Transactional
    @Override
    @CacheEvict(value = "PostTitle", allEntries=true)
    public void deleteComment(@ExistsCommentId String commentId) {
        commentRepository.deleteByCommentsId(commentId);
    }

    @Override
    public Collection<?> searchComment(String keyword) {
        return commentRepository.searchCommentIDandBody(keyword, CommentExceptr.class);
    }

}
