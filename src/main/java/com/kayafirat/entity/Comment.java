package com.kayafirat.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "comment")
public class Comment extends JdkSerializationRedisSerializer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "comment_Id")
    private String commentId;

    @NotEmpty(message = "{validation.userName.notEmpty}")
    @Column(name = "user_name")
    private String username;

    @NotEmpty(message = "{validation.profilephoto.notEmpty}")
    @Column(name = "user_profil_photo")
    private String userProfilePhoto;

    @NotEmpty(message = "{validation.commentMessage.notEmpty}")
    @Column(name = "comment_Message")
    private String commentMessage;

    @Column(name = "comment_Time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String commentTime;

    @Column(name = "comment_Updated_Time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private String commentUpdateTime;

    @Column(name = "is_updated", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isUpdated;

    @Column(name = "is_hide", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isHide;

    @JsonIgnoreProperties("comment")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_Id")
    private Post post;

}
