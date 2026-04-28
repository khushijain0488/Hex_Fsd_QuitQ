package com.quitq.service;

import com.quitq.Mapper.UserMapper;
import com.quitq.enums.Role;
import com.quitq.dto.UserResponsedto;
import com.quitq.execption.ResourceNotFoundException;
import com.quitq.model.User;
import com.quitq.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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


    // ─────────────────────────────────────────────
    //  getById Tests
    // ─────────────────────────────────────────────

    @Test
    public void getByIdTestWhenUserExists() {

        // Confirm service is wired
        Assertions.assertNotNull(userService);

        // Prepare mock User
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setEmail("john@example.com");

        // Mock: when findById(1L) is called, return this user
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getById(1L);

        // Assert: returned user matches mocked user
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("john_doe", result.getUsername());
        Assertions.assertEquals("john@example.com", result.getEmail());

        // Verify repository was called exactly once
        Mockito.verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void getByIdTestWhenUserNotFound() {

        // Mock: when findById(99L) is called, return empty
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert: expect ResourceNotFoundException
        Exception e = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.getById(99L);
        });

        // Verify exception message
        Assertions.assertEquals("The id is invalid", e.getMessage());

        // Verify repository was called once
        Mockito.verify(userRepository, times(1)).findById(99L);
    }


    // ─────────────────────────────────────────────
    //  getAllUsers Tests
    // ─────────────────────────────────────────────

    @Test
    public void getAllUsersTestReturnsAllUsers() {

        // Prepare mock User list
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("john_doe");
        user1.setEmail("john@example.com");
        user1.setRole(Role.USER);

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("jane_doe");
        user2.setEmail("jane@example.com");
        user2.setRole(Role.USER);

        List<User> mockUserList = List.of(user1, user2);

        // Mock: when findAll() is called, return the list
        when(userRepository.findAll()).thenReturn(mockUserList);

        // Act
        List<UserResponsedto> result = userService.getAllUsers();

        // Assert: size matches
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());

        // Verify repository was called exactly once
        Mockito.verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getAllUsersTestWhenNoUsersExistReturnsEmptyList() {

        // Mock: return empty list
        when(userRepository.findAll()).thenReturn(List.of());

        // Act
        List<UserResponsedto> result = userService.getAllUsers();

        // Assert: empty list returned, no exception thrown
        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());

        Mockito.verify(userRepository, times(1)).findAll();
    }
}