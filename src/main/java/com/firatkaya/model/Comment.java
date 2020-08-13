package com.firatkaya.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

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
@Table(name = "comment")
public class Comment  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "comment_Id")
	private String commentId;

	@NotEmpty(message = "{validation.userName.notEmpty}")
	@Column(name = "user_name")
	private String username;
	
	@NotEmpty(message = "{validation.profilephoto.notEmpty}")
	@Column(name = "user_profil_photo")
	private String userProfilPhoto;
	
	@NotEmpty(message = "{validation.commentMessage.notEmpty}")
	@Column(name = "comment_Message")
	private String commentMessage;
	
	@Column(name = "comment_Time",columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private String commentTime;
	
	@JsonIgnoreProperties("comment")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_Id")
    private Post post;

}
