package com.firatkaya.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.firatkaya.model.Comment;

@Service
public interface CommentService {
	
	List<Comment> getAllComments(String postId);
	
	Comment getOneComment(String commentId);
	
	Comment saveComment(Comment comment,String postId);
	
	Comment updateComment(Comment comment);
	
	
	boolean deleteComment(String commentId);
	
	Collection<?> searchComment(String keyword);


}
