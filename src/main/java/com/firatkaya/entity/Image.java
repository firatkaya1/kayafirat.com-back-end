package com.firatkaya.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "image_created_date")
    private String imageCreatedDate;
}
