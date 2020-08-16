package com.firatkaya.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

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
    @Email(message = "{validation.useremail.email}")
    private String userEmail;

    @Column(name = "user_Id", nullable = false, unique = true)
    private String userId;

    @NotEmpty(message = "{validation.username.notEmpty}")
    @Size(min = 5, max = 15, message = "{validation.username.lenght}")
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @NotEmpty(message = "{validation.userpassword.notEmpty}")
    @Size(min = 5, max = 45, message = "{validation.userpassword.lenght}")
    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_register_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date userRegisterDate;

    @Column(name = "user_birthday_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String userBirthdayDate;

    @NotEmpty(message = "{validation.profilephoto.notEmpty}")
    @Column(name = "user_profile_photo", nullable = false)
    private String userProfilePhoto;

    @Column(name = "is_verification", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isVerification;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "userProfile_fk", referencedColumnName = "userEmail")
    private UserProfile userProfile;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "userPermissions_fk", referencedColumnName = "userEmail")
    private UserPermissions userPermissions;


}
