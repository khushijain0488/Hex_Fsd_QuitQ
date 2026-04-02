package com.quitq.dto;

public record LoginResponseDTO(
        String token,
        String role,
        String email
) {}