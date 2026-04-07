package com.taskmanagement.Dto;

import com.taskmanagement.enums.Role;

public record UserResponseDto(
Long id,
String name,
String email,
Role role,
String contactNumber,
String address,
String gender,
String authority
) {
}
