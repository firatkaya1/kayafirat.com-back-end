package com.firatkaya.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "postseo")
public class    PostSeo extends JdkSerializationRedisSerializer implements Serializable {

    @Id
    @Column(name = "post_Id")
    private String postId;

    @Column(name = "metaDescription")
    private String metaDescription;

    @Column(name = "metaKeywords")
    private String metaKeywords;

    @Column(name = "metaAuthor")
    private String metaAuthor;

    @Column(name = "metaOgTitle")
    private String metaOgTitle;

    @Column(name = "metaOgDescription")
    private String metaOgDescription;

    @Column(name = "metaOgURL")
    private String metaOgURL;

    @Column(name = "metaOgSitename")
    private String metaOgSitename;

    @Column(name = "metaOgImage")
    private String metaOgImage;

    @Column(name = "metaOgType")
    private String metaOgType;

    @Column(name = "metaTwitterCard")
    private String metaTwitterCard;

    @Column(name = "metaTwitterSite")
    private String metaTwitterSite;

    @Column(name = "metaTwitterCreator")
    private String metaTwitterCreator;

    @Column(name = "metaTwitterTitle")
    private String metaTwitterTitle;

    @Column(name = "metaTwitterDescription")
    private String metaTwitterDescription;

    @Column(name = "metaTwitterImage")
    private String metaTwitterImage;


}
