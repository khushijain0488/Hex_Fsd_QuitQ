package com.quitq.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(





        @NotNull(message = "Order items are required")
        List<OrderItemRequestDTO> items


) {}