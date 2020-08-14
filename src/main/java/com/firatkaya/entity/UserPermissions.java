package com.firatkaya.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user_permissions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissions {

    @Id
    @Column(name = "userEmail")
    private String userEmail;

    @Column(name = "isNameShow", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isNameShow;

    @Column(name = "isAboutmeShow", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isAboutmeShow;

    @Column(name = "isRegisterdateShow", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isRegisterdateShow;

    @Column(name = "isBirthdateShow", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isBirthdateShow;

    @Column(name = "isContactShow", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isContactShow;

    @Column(name = "isLastSeenShow", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isLastSeenShow;

    @Column(name = "isAllCommentShow", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isAllCommentShow;

    @Column(name = "isAllFavShow", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isAllFavShow;

    @Column(name = "isGithubShow", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isGithubShow;

    @Column(name = "isLinkedinShow", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isLinkedinShow;


}
