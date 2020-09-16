package com.firatkaya.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.firatkaya.entity.Comment;

public interface CommentService {

    List<Comment> getAllComments(String postId);

    Comment saveComment(Comment comment, String postId);

    boolean updateComment(HashMap<String,String> request);

    boolean deleteComment(String commentId,String postId);

    Collection<?> searchComment(String keyword);


}
