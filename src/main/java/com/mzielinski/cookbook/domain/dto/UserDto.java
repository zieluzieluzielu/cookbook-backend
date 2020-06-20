package com.mzielinski.cookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserDto {
    private Long userId;
    private String userName;
    private String emailAddress;
    private String userPassword;
    private boolean isLogged;

    public UserDto(String userName, String emailAddress, String userPassword, boolean isLogged) {
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.userPassword = userPassword;
        this.isLogged = isLogged;
    }
}
