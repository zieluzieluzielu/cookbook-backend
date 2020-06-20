package com.mzielinski.cookbook.controller;

import com.mzielinski.cookbook.domain.dto.UserDto;
import com.mzielinski.cookbook.exception.UserNotFoundException;
import com.mzielinski.cookbook.mapper.UserMapper;
import com.mzielinski.cookbook.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<UserDto> getUsers() {
        return userMapper.mapToUsersDtoList(userService.getAllUsers());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userMapper.mapToUserDto(userService.getUser(userId).orElseThrow(() -> new UserNotFoundException("User: " + userId + " was not found")));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users", consumes = APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody UserDto userDto) {
        LOGGER.info("Creating new User");
        return userMapper.mapToUserDto(userService.saverUser(userMapper.mapToUser(userDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users")
    public UserDto updateUser(@RequestBody UserDto userDto) {
        LOGGER.info("User has been updated");
        return userMapper.mapToUserDto(userService.saverUser(userMapper.mapToUser(userDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        LOGGER.info("Deleting user with id: " + userId);
        userService.deleteUser(userId);
    }


}
