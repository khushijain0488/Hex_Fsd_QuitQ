package com.taskmanagement.Dto;

import com.taskmanagement.enums.Role;


public record UserRequestDto(
         String name,


 String email,


 String password,


 Role role,

 String contactNumber,
 String address,
 String gender
) {
}
