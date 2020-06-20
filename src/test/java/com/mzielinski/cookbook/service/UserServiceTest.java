package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.User;
import com.mzielinski.cookbook.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void getAllUsers() {
        //Given
        List<User> userList = new ArrayList<>();
        User user = new User();
        userList.add(user);

        when(userRepository.findAll()).thenReturn(userList);

        //When
        List<User> allUsers = userService.getAllUsers();

        //Then
        assertEquals(1, allUsers.size());

    }

    @Test
    public void getUser() {
        //Given
        User user = new User(1L,"mark_hamill","mark@hamill.com","Mark123",true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //When
        Optional<User> getUser = userService.getUser(1L);
        //Then
        assertTrue(getUser.isPresent());
        assertEquals("mark_hamill", getUser.get().getUserName());
        assertTrue(getUser.get().isLogged());
    }


    @Test
    public void saveUser() {
        //Given
        User user = new User(1L,"mark_hamill","mark@hamill.com","Mark123",true);

        when(userRepository.save(user)).thenReturn(user);
        //When
        User savedUser = userService.saverUser(user);
        //Then
        assertEquals("mark_hamill", savedUser.getUserName());
        assertTrue(savedUser.isLogged());
    }

    @Test
    public void deleteUser() {
        //Given
        User user = new User(1L,"mark_hamill","mark@hamill.com","Mark123",true);

        //When
        userService.deleteUser(1L);

        //Then
        verify(userRepository, times(1)).deleteById(anyLong());
        assertFalse(userRepository.findById(user.getUserId()).isPresent());

    }
}
