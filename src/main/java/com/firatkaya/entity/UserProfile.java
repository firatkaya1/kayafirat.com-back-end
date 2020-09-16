package com.firatkaya.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;

@Entity
@Table(name = "userProfile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile extends JdkSerializationRedisSerializer implements Serializable  {

    @Id
    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "about_me")
    private String userAboutme;

    @Column(name = "user_github")
    private String userGithub;

    @Column(name = "user_linkedin")
    private String userLinkedin;


}
