package com.mzielinski.cookbook.controller;

import com.google.gson.Gson;
import com.mzielinski.cookbook.domain.*;
import com.mzielinski.cookbook.domain.dto.UserDto;
import com.mzielinski.cookbook.mapper.UserMapper;
import com.mzielinski.cookbook.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;


    @Test
    public void shouldRetrieveUserList() throws Exception {
        //Given
        List<UserDto> usersListDto = new ArrayList<>();
        UserDto userDto1 = UserDto.builder()
                .userId(1L)
                .userName("testUser1")
                .emailAddress("test1@email.com")
                .userPassword("userPassword1")
                .isLogged(true)
                .build();


        UserDto userDto2 = UserDto.builder()
                .userId(2L)
                .userName("testUser2")
                .emailAddress("test12email.com")
                .userPassword("userPassword2")
                .isLogged(false)
                .build();

        UserDto userDto3 = UserDto.builder()
                .userId(3L)
                .userName("testUser3")
                .emailAddress("test3@email.com")
                .userPassword("userPassword3")
                .isLogged(false)
                .build();


        usersListDto.add(userDto1);
        usersListDto.add(userDto2);
        usersListDto.add(userDto3);


        when(userMapper.mapToUsersDtoList(userService.getAllUsers())).thenReturn(usersListDto);


        //When & Then
        mockMvc.perform(get("/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].userId", is(usersListDto.get(0).getUserId().intValue())))
                .andExpect(jsonPath("$[1].userName", is("testUser2")))
                .andExpect(jsonPath("$[2].emailAddress", is("test3@email.com")))
                .andExpect(jsonPath("$[0].userPassword", is("userPassword1")))
                .andExpect(jsonPath("$[0].logged", is(true)))
                .andExpect(jsonPath("$[1].logged", is(false)));
    }


    @Test
    public void shouldRetrieveUser() throws Exception {
        //Given
        UserDto userDto = UserDto.builder()
                .userId(1L)
                .userName("testUser")
                .emailAddress("test@email.com")
                .userPassword("userPassword")
                .isLogged(true)
                .build();
        User user1 = new User(1L, "testUser", "test@email.com", "userPassword", true);
        Optional<User> user = Optional.of(user1);

        when(userService.getUser(any())).thenReturn(user);
        when(userMapper.mapToUserDto(any())).thenReturn(userDto);

        //When & Then
        mockMvc.perform(get("/v1/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.userName", is("testUser")))
                .andExpect(jsonPath("$.emailAddress", is("test@email.com")))
                .andExpect(jsonPath("$.userPassword", is("userPassword")))
                .andExpect(jsonPath("$.logged", is(true)));

    }


    @Test
    public void shouldUpdateUser() throws Exception {
        //Given
        UserDto userDto = UserDto.builder()
                .userId(1L)
                .userName("testUser")
                .emailAddress("test@email.com")
                .userPassword("userPassword")
                .isLogged(true)
                .build();
        User user1 = new User(1L, "testUser", "test@email.com", "userPassword", true);
        Optional<User> user = Optional.of(user1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        when(userService.getUser(any())).thenReturn(user);
        when(userMapper.mapToUserDto(any())).thenReturn(userDto);

        //When & Then
        mockMvc.perform(put("/v1/users").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.userName", is("testUser")))
                .andExpect(jsonPath("$.emailAddress", is("test@email.com")))
                .andExpect(jsonPath("$.userPassword", is("userPassword")))
                .andExpect(jsonPath("$.logged", is(true)));


    }


    @Test
    public void shouldCreateUser() throws Exception {
        //Given
        UserDto userDto = UserDto.builder()
                .userId(1L)
                .userName("testUser")
                .emailAddress("test@email.com")
                .userPassword("userPassword")
                .isLogged(true)
                .build();
        User user1 = new User(1L, "testUser", "test@email.com", "userPassword", true);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        when(userMapper.mapToUser(any(UserDto.class))).thenReturn(user1);
        when(userMapper.mapToUserDto(user1)).thenReturn(userDto);
        when(userService.saverUser(any(User.class))).then(returnsFirstArg());

        System.out.println(jsonContent);
        //When & Then
        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.userName", is("testUser")))
                .andExpect(jsonPath("$.emailAddress", is("test@email.com")))
                .andExpect(jsonPath("$.userPassword", is("userPassword")))
                .andExpect(jsonPath("$.logged", is(true)));


    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //When&Then
        mockMvc.perform(delete("/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(1L);
    }


}