package com.taskmanagement.Dto;

public record LoginResponseDTO(
        String token,
        String role,
        String email
) {}
