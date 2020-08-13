package com.firatkaya.service.Impl;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firatkaya.exceptions.CommentNotFoundException;
import com.firatkaya.exceptions.PostNotFoundException;
import com.firatkaya.model.Comment;
import com.firatkaya.model.CommentExceptr;
import com.firatkaya.model.Post;
import com.firatkaya.model.User;
import com.firatkaya.repository.CommentRepository;
import com.firatkaya.repository.PostRepository;
import com.firatkaya.service.CommentService;
import com.firatkaya.service.UserService;

@Service
public class CommentServiceImp implements CommentService{

	private static final String DEFAULT_PROFIL_PHOTO="assets/images/profile.svg";
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserService userService;
	
	@Override
	public List<Comment> getAllComments(String postId) {
		if(!postRepository.existsByPostId(postId))
			throw new PostNotFoundException(postId);
		
		return commentRepository.findAll();
	} 

	@Override
	public Comment getOneComment(String commentId) {
		Comment comment =commentRepository.findByCommentId(commentId);
		if(comment == null) 
			 throw new CommentNotFoundException(commentId);
		
		return comment;
	}

	@Transactional
	@Override
	public Comment saveComment(Comment comment,String postId) {
		Post post = postRepository.findByPostId(postId);
		User user = userService.getUser(comment.getUsername());
		comment.setCommentId(UUID.randomUUID().toString());
		comment.setPost(post);
		if(comment.getUsername().equals("Anonymous")) {
			comment.setUserProfilPhoto(DEFAULT_PROFIL_PHOTO);
		} else {
			comment.setUserProfilPhoto(user.getUserProfilPhoto());
			comment.setUsername(user.getUserName());
		}
		
		postRepository.updateMaxComment(postId);
		return commentRepository.save(comment);
	}

	@Override
	public Comment updateComment(Comment comment) {
		if(!commentRepository.existsById(comment.getCommentId()))
			throw new CommentNotFoundException(comment.getCommentId());
		
		return commentRepository.save(comment);
	}
	
	@Transactional
	@Override
	public boolean deleteComment(String commentId) {
		if(!commentRepository.existsById(commentId))
			throw new CommentNotFoundException(commentId);
		
		commentRepository.deleteByCommentsId(commentId);
		return true;
	}

	@Override
	public Collection<?> searchComment(String keyword) {
		return commentRepository.searchCommentIDandBody(keyword, CommentExceptr.class);
	}

}
