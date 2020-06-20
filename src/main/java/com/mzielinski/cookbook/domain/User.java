package com.mzielinski.cookbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "USERS")
public class User {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID", unique = true)
    private Long userId;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "E_MAIL")
    private String emailAddress;

    @Column(name = "PASSWORD")
    private String userPassword;

    @Column(name = "LOGGED")
    private boolean isLogged;

    public User(String userName, String emailAddress, String userPassword, boolean isLogged) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.userPassword = userPassword;
        this.isLogged = isLogged;
    }
}
