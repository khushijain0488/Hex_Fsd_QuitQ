package com.quitq.service;

import com.quitq.Mapper.UserMapper;
import com.quitq.dto.UserResponsedto;
import com.quitq.execption.ResourceNotFoundException;
import com.quitq.model.User;
import com.quitq.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getById(@NotNull(message = "Seller ID is required") Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The id is invalid"));

    }
    private final PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<UserResponsedto> getAllUsers() {
        List<User>userList= userRepository.findAll();
        return userList.stream().map(UserMapper::maptoDto).toList();
    }
}
