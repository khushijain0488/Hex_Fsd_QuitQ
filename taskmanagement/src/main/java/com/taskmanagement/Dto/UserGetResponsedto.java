package com.taskmanagement.Dto;

import com.taskmanagement.Model.User;

import java.util.List;

public record UserGetResponsedto(
        List<UserResponseDto> data,
        Long totalElement,
        int totalPages
) {
}
