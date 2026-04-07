package com.taskmanagement.Mapper;

import com.taskmanagement.Dto.UserResponseDto;
import com.taskmanagement.Model.User;

public class UserMapper {
    public static UserResponseDto MapToDto(User user){
     return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getContactNumber(),
             user.getAddress(),user.getGender(),user.getAuthorities().toString());
    }
}
