package com.firatkaya.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "post_Id",nullable = false)
	private String postId;
	
	@Column(name = "post_Tag",nullable = false)
	private String postTag;
	
	@Column(name = "post_Title",nullable = false)
	private String postTitle;
	
	@Column(name = "post_Header",nullable = false)
	private String postHeader;
	
	@Column(name = "post_Body", columnDefinition = "TEXT")
	private String postBody;
	
	@Column(name = "post_Time", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private String postTime;
	
	@Column(name = "post_MaxView", columnDefinition = "bigint(20) default 0" )
	private long postMaxView;
	
	@Column(name = "post_MaxComment", columnDefinition = "bigint(20) default 0" )
	private long postMaxComment;
	
	@JsonIgnoreProperties("post")
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "post",orphanRemoval=true)
	@OrderBy("comment_time")
	private Set<Comment> comment= new HashSet<>();
	
	
}
