package com.quitq.dto;

public record LoginRequestDTO(
        String email,
        String password
) {}