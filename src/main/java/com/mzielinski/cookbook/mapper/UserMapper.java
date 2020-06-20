package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.User;
import com.mzielinski.cookbook.domain.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getUserName(),
                userDto.getEmailAddress(),
                userDto.getUserPassword(),
                userDto.isLogged()
        );
    }

    public UserDto mapToUserDto(final User user) {
        return new UserDto(
                user.getUserName(),
                user.getEmailAddress(),
                user.getUserPassword(),
                user.isLogged()
        );
    }


    public List<UserDto> mapToUsersDtoList(final List<User> usersList) {
        return usersList.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

}
