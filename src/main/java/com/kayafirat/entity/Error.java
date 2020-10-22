package com.kayafirat.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "error")
public class Error {

    @Id
    @GeneratedValue
    private Long errorId;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "email")
    private String email;

    @Column(name = "error_message")
        private String errorMessage;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdDate;

    @Column(name = "is_read")
    private boolean isRead;
}