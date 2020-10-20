package com.firatkaya.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.firatkaya.model.projection.CommentDetailExcept;
import com.firatkaya.model.projection.CommentExceptr;

import com.firatkaya.entity.Comment;

public interface CommentService {

    CommentDetailExcept getAllCommentsDetails(String commentId);

    List<CommentExceptr> getAllComments( );

    List<Comment> getAllComments(String postId);

    Comment saveComment(Comment comment, String postId );

    void updateComment(HashMap<String,String> request);

    void deleteComment(String commentId, String postId);

    Collection<?> searchComment(String keyword);


}
