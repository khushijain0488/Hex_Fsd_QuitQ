package com.quitq.dto;

import com.quitq.enums.Role;

public record UserRequestDto(
        String username,
        String email,
        String password,
        Role role,
        String contactNumber,
        String address,
        String gender
) {
}
