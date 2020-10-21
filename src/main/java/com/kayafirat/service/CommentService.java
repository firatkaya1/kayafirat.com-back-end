package com.kayafirat.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.kayafirat.model.projection.CommentDetailExcept;
import com.kayafirat.model.projection.CommentExceptr;

import com.kayafirat.entity.Comment;

public interface CommentService {

    CommentDetailExcept getAllCommentsDetails(String commentId);

    List<CommentExceptr> getAllComments( );

    List<Comment> getAllComments(String postId);

    Comment saveComment(Comment comment, String postId );

    void updateComment(HashMap<String,String> request);

    void deleteComment(String commentId);

    Collection<?> searchComment(String keyword);


}
