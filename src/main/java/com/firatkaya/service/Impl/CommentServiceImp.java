package com.firatkaya.service.Impl;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.firatkaya.model.Comment;
import com.firatkaya.model.CommentExceptr;
import com.firatkaya.model.Post;
import com.firatkaya.repository.CommentRepository;
import com.firatkaya.repository.PostRepository;
import com.firatkaya.service.CommentService;

@Service
public class CommentServiceImp implements CommentService{

	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Override
	public List<Comment> getAllComments() {
		return commentRepository.getAll();
	}
	
	@Override
	public List<Comment> getAllComments(String postId) {
		return null;
	} 

	@Override
	public Comment getOneComment(String commentId) {
		return commentRepository.findByCommentId(commentId);
	}

	@Transactional
	@Override
	public Comment saveComment(Comment comment,String postId) {
		Post post = postRepository.findByPostId(postId);
		comment.setCommentId(UUID.randomUUID().toString());
		comment.setPost(post);
		postRepository.updateMaxComment(postId);
		return commentRepository.save(comment);
	}

	@Override
	public Comment updateComment(Comment comment) {
		return commentRepository.save(comment);
	}
	
	@Transactional
	@Override
	public boolean deleteComment(String commentId) {
		commentRepository.deleteByCommentsId(commentId);
		return true;
	}

	@Override
	public Collection<?> searchComment(String keyword) {
		return commentRepository.searchCommentIDandBody(keyword, CommentExceptr.class);
	}

}
