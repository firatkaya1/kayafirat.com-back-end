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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "post_Id",nullable = false)
	private String postId;
	
	@NotEmpty(message = "{validation.postTag.notEmpty}")
	@Min(value = 2, message = "{validation.postTag.minLenght}")
	@Max(value = 10,message = "{validation.postTag.maxLenght}")
	@Column(name = "post_Tag",nullable = false)
	private String postTag;

	@NotEmpty(message = "{validation.postTitle.notEmpty}")
	@Min(value = 3, message = "{validation.postTitle.minLenght}")
	@Max(value = 40,message = "{validation.postTitle.maxLenght}")
	@Column(name = "post_Title",nullable = false)
	private String postTitle;
	
	@NotEmpty(message = "{validation.postHeader.notEmpty}")
	@Min(value = 5, message = "{validation.postHeader.minLenght}")
	@Max(value = 45,message = "{validation.postHeader.maxLenght}")
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
