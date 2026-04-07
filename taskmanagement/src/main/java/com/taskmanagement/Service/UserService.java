package com.taskmanagement.Service;

import com.taskmanagement.Dto.UserGetResponsedto;
import com.taskmanagement.Dto.UserRequestDto;
import com.taskmanagement.Dto.UserResponseDto;
import com.taskmanagement.Exception.ResourceNotFoundException;
import com.taskmanagement.Mapper.UserMapper;
import com.taskmanagement.Model.User;
import com.taskmanagement.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public UserGetResponsedto getAllUser(int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<User>userList= userRepository.findAll(pageable);
        List<UserResponseDto>userResponseDtoList=userList.toList().stream().map(UserMapper::MapToDto).toList();
        Long totalElement=userList.getTotalElements();
        int totalPages=userList.getTotalPages();
        return new UserGetResponsedto(userResponseDtoList,totalElement,totalPages);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The given id is invalid"));
    }

    public void updateUserById(Long id, UserRequestDto userRequestDto) {
//        step-1 chechking user exist with id or not
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("The given id is invalid"));
//        step-2 updating details from dto
        user.setName(userRequestDto.name());
        user.setEmail(userRequestDto.email());
        user.setPassword(passwordEncoder.encode(userRequestDto.password()));
        user.setRole(userRequestDto.role());
        user.setContactNumber(userRequestDto.contactNumber());
        user.setAddress(userRequestDto.address());
        user.setGender(userRequestDto.gender());
//        step-3 save this in db
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
//        find whether the user exist with given id or not
        User user=getUserById(id);
//        if it is their then delete it
        userRepository.deleteById(id);
    }
}
