package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.User;
import com.mzielinski.cookbook.domain.dto.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {
    @InjectMocks
    UserMapper userMapper;

    @Test
    public void mapToUserTest() {
        //Given
        UserDto userDto = new UserDto(1L, "testUser","test@email.com","userPassword",true);


        //When
        User user = userMapper.mapToUser(userDto);

        //Then
        assertEquals(userDto.getUserName(), user.getUserName());
    }

    @Test
    public void mapToUserDtoTest() {
        //Given
        User user = new User(1L,"mark_hamill","mark@hamill.com","Mark123",true);

        //When
        UserDto userDto = userMapper.mapToUserDto(user);
        //Then
        assertEquals(user.getUserName(), userDto.getUserName());
    }

    @Test
    public void mapToUserDtoListTest() {
        //Given
        List<User> usersList = new ArrayList<>();

        User user1 = new User(1L,"mark_hamill","mark@hamill.com","Mark123",true);
        User user2 = new User(2L,"han_solo","han@solo.com","HanSolo123",true);

        usersList.add(user1);
        usersList.add(user2);


        //When
        List<UserDto> userDtoList = userMapper.mapToUsersDtoList(usersList);

        //Then
        assertEquals(2, userDtoList.size());
        assertEquals("mark_hamill", userDtoList.get(0).getUserName());
        assertEquals("han_solo", userDtoList.get(1).getUserName());
        assertEquals("mark@hamill.com", userDtoList.get(0).getEmailAddress());
        assertEquals("HanSolo123", userDtoList.get(1).getUserPassword());
    }
}
