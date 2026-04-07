package com.taskmanagement.Service;


import com.taskmanagement.Exception.ResourceNotFoundException;
import com.taskmanagement.Model.User;
import com.taskmanagement.Repository.UserRepository;
import com.taskmanagement.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    public void getUserByIdWhenExists() {
        // Check userService is not null
        Assertions.assertNotNull(userService);

        // Preparing mock user
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@gmail.com");
        user.setRole(Role.USER);
        user.setContactNumber("9999999999");
        user.setAddress("Kota, Rajasthan");
        user.setGender("Male");

        // Mocking repository
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Preparing expected user object
        User expectedUser = userRepository.findById(1L).get();

        // Wrong user to verify assertNotEquals
        User wrongUser = new User();
        wrongUser.setId(99L);
        wrongUser.setName("Wrong User");

        // Assertions
        Assertions.assertEquals(expectedUser, userService.getUserById(1L));
        Assertions.assertNotEquals(wrongUser, userService.getUserById(1L));

        // Verify findById called 3 times (once for expectedUser, twice for assertions)
        Mockito.verify(userRepository, times(3)).findById(1L);
    }


    @Test
    public void getUserByIdWhenNotFound() {
        // Mock: return empty when 99L is passed
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Verify exception is thrown
        Exception e = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(99L);
        });

        // Check exception message
        Assertions.assertEquals("The given id is invalid", e.getMessage());
    }


    @Test
    public void getAllUserTest() {
        // Prepare mock users
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Harry");
        user1.setEmail("harry@gmail.com");
        user1.setRole(Role.USER);
        user1.setContactNumber("9999999999");
        user1.setAddress("London");
        user1.setGender("Male");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Ankit");
        user2.setEmail("ankit@gmail.com");
        user2.setRole(Role.ADMIN);
        user2.setContactNumber("8888888888");
        user2.setAddress("Jaipur");
        user2.setGender("Male");

        List<User> list = List.of(user1, user2);

        // Page with size 1
        Page<User> pageUser1 = new PageImpl<>(list.subList(0, 1));
        Pageable pageable1 = PageRequest.of(0, 1);

        // Mock repository call
        when(userRepository.findAll(pageable1)).thenReturn(pageUser1);

        // Actual call and assertion
        Assertions.assertEquals(1, userService.getAllUser(0, 1).data().size());
    }
}