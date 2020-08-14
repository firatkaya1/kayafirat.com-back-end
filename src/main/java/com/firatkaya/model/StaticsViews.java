package com.firatkaya.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "staticsview")
public class StaticsViews {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "IpAddress", nullable = false)
    private String ipAddress;

    @Column(name = "temporaryCode", nullable = false)
    private String temporaryCode;

    @Column(name = "viewDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date viewDate;

    @Column(name = "postId", nullable = false)
    private String postId;


}
