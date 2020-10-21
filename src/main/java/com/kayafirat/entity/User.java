package com.kayafirat.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;


@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class User extends JdkSerializationRedisSerializer implements Serializable {

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
    @Size(min = 5 , message = "{validation.userpassword.lenght}")
    @Column(name = "user_password", nullable = false)
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    @Column(name = "user_role")
    private String role;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "userProfile_fk", referencedColumnName = "userEmail")
    private UserProfile userProfile;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "userPermissions_fk", referencedColumnName = "userEmail")
    private UserPermissions userPermissions;


}
