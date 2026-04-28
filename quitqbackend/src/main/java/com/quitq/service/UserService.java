package com.quitq.service;

import com.quitq.Mapper.UserMapper;
import com.quitq.dto.UserRequestDto;
import com.quitq.dto.UserResponsedto;
import com.quitq.execption.ResourceNotFoundException;
import com.quitq.model.User;
import com.quitq.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public User getById(@NotNull(message = "Seller ID is required") Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The id is invalid"));

    }




    public List<UserResponsedto> getAllUsers() {
        List<User>userList= userRepository.findAll();
        return userList.stream().map(UserMapper::maptoDto).toList();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username);
    }

    public void registerUser(UserRequestDto userRequestDto) {
        User user=new User();
        user.setUsername(userRequestDto.username());
        user.setEmail(userRequestDto.email());
        user.setPassword(passwordEncoder.encode(userRequestDto.password()));
        user.setRole(userRequestDto.role());
        user.setContactNumber(userRequestDto.contactNumber());
        user.setAddress(userRequestDto.address());
        user.setGender(userRequestDto.gender());
        userRepository.save(user);


    }

    public User getLoggedInUser(String name) {
        return userRepository.loadByUsername(name);
    }


}
