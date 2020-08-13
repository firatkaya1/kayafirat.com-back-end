package com.firatkaya.entity;

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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "post_Id",nullable = false)
	private String postId;
	
	@NotEmpty(message = "{validation.postTag.notEmpty}")
	@Size(min = 2, max = 10, message = "{validation.postTag.length}")
	@Column(name = "post_Tag",nullable = false)
	private String postTag;

	@NotEmpty(message = "{validation.postTitle.notEmpty}")
	@Size(min = 3, max = 40, message = "{validation.postTitle.length}")
	@Column(name = "post_Title",nullable = false)
	private String postTitle;
	
	@NotEmpty(message = "{validation.postHeader.notEmpty}")
	@Size(min = 5, max = 45, message = "{validation.postTitle.length}")
	@Column(name = "post_Header",nullable = false)
	private String postHeader;
	
	@NotEmpty(message = "{validation.postBody.notEmpty}")
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
