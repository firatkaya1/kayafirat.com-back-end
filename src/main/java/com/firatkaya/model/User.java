package com.firatkaya.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	private String userEmail;
	
	@Column(name = "user_Id",nullable = false, unique=true)
	private String userId;
	
	@Column(name = "user_name",nullable = false, unique=true)
    private String userName;
	
	@Column(name = "user_password",nullable = false)
	private String userPassword;
	
	@Column(name = "user_register_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date userRegisterDate;
	
	@Column(name = "user_birthday_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date userBirthdayDate;
	
	@Column(name = "user_profile_photo",nullable = false)
	private String userProfilPhoto;
	
	@Column(name = "is_verification", columnDefinition="BOOLEAN DEFAULT false")
	private boolean isVerification;
	
	@OneToOne(cascade = {CascadeType.ALL},  fetch = FetchType.EAGER)
    @JoinColumn(name = "userProfile_fk",referencedColumnName = "userEmail")
	private UserProfile userProfile;
	
	@OneToOne(cascade = {CascadeType.ALL},  fetch = FetchType.EAGER)
    @JoinColumn(name = "userPermissions_fk",referencedColumnName = "userEmail")
	private UserPermissions userPermissions;

	
	
}
